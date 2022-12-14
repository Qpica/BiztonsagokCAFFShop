package com.Biztonsagok.CAFFShop.services;

import com.Biztonsagok.CAFFShop.dto.UserRequestDTO;
import com.Biztonsagok.CAFFShop.dto.UserResponseDTO;
import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.models.UserRole;
import com.Biztonsagok.CAFFShop.models.UserRoleType;
import com.Biztonsagok.CAFFShop.repositories.UserRepository;
import com.Biztonsagok.CAFFShop.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private PasswordEncoder encoder;


	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<UserResponseDTO> getUserResponseDTOByUserName(String  userName) {
		Optional<User> resultUser = userRepository.findByUsername(userName);
		return resultUser.map(this::userResponseDTOFromUserSimple);
	}

	public UserResponseDTO userResponseDTOFromUserSimple(User user) {
		UserResponseDTO result = new UserResponseDTO();
		result.setUserName(Objects.requireNonNull(user.getUsername()));
		result.setRoles(Objects.requireNonNullElse(user.getRoles(), new HashSet<>()));
		return result;
	}

	public Optional<User> registerUser(UserRequestDTO userRequestDTO) {
		Set<UserRole> roles = new HashSet<>();
		Optional<UserRole> userRole = userRoleRepository.findByRoleName(UserRoleType.ROLE_USER);
		User user = new User(userRequestDTO.getUsername(), encoder.encode(userRequestDTO.getPassword()));
		userRole.ifPresent(roles::add);
		user.setRoles(roles);
		User userToRegister = userRepository.save(user);
		return Optional.of(userToRegister);
	}

	public Optional<User> deleteUserById(String id) {
		Optional<User> userToDelete = userRepository.findByUsername(id);
		userToDelete.ifPresent(user -> userRepository.deleteById(user.getId()));
		return userToDelete;
	}

	public Optional<User> updateUser(String id, UserRequestDTO userRequestDTO) {
		Optional<User> userToUpdate = userRepository.findByUsername(id);
		if(userToUpdate.isPresent()){
			updateUserFrom(userRequestDTO, userToUpdate.get());
			userRepository.save(userToUpdate.get());
			return userToUpdate;
		}
		return userToUpdate;
	}

	private void updateUserFrom(UserRequestDTO userRequestDTO, User user) {
		//user.setUsername(Objects.requireNonNullElse(userRequestDTO.getUsername(), user.getUsername()));
		user.setRoles(Objects.requireNonNullElse(userRequestDTO.getRoles(), user.getRoles()));
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void createUser(String username, String password) {
		userRepository.save(new User(username, password));
	}
}
