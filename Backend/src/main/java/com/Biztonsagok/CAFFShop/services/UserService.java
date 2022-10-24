package com.Biztonsagok.CAFFShop.services;

import com.Biztonsagok.CAFFShop.dto.UserRequestDTO;
import com.Biztonsagok.CAFFShop.dto.UserResponseDTO;
import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.repositories.CaffPictureRepository;
import com.Biztonsagok.CAFFShop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CaffPictureRepository caffPictureRepository;
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
		User userToRegister = userRepository.save(
				new User(userRequestDTO.getUsername(), encoder.encode(userRequestDTO.getPassword()))
		);
		return Optional.of(userToRegister);
	}

	public Optional<User> deleteUserById(UUID id) {
		Optional<User> userToDelete = userRepository.findById(id);
		if(userToDelete.isPresent()){
			userRepository.deleteById(id);
		}
		return userToDelete;
	}

	public Optional<User> updateUser(UUID id, UserRequestDTO userRequestDTO) {
		Optional<User> userToUpdate = userRepository.findById(id);
		if(userToUpdate.isPresent()){
			updateUserFrom(userRequestDTO, userToUpdate.get());
			userRepository.save(userToUpdate.get());
			return userToUpdate;
		}
		return userToUpdate;
	}

	private void updateUserFrom(UserRequestDTO userRequestDTO, User user) {
		user.setUsername(Objects.requireNonNullElse(userRequestDTO.getUsername(), user.getUsername()));
		user.setRoles(Objects.requireNonNullElse(userRequestDTO.getRoles(), user.getRoles()));
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void createUser(String username, String password) {
		userRepository.save(new User(username, password));
	}
}
