package com.Biztonsagok.CAFFShop.controllers;

import com.Biztonsagok.CAFFShop.dto.UserRequestDTO;
import com.Biztonsagok.CAFFShop.dto.UserResponseDTO;
import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		List<UserResponseDTO> responseDTOList = userService.getAllUsers().stream()
				.map(
						user -> userService.userResponseDTOFromUserSimple(user)
				).collect(Collectors.toList());
		return ResponseEntity.ok(responseDTOList);
	}

	@GetMapping("/{userName}")
	public ResponseEntity<UserResponseDTO> getUserByUserName(@PathVariable String userName){
		Optional<UserResponseDTO> result = userService.getUserResponseDTOByUserName(userName);
		return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO){
		Optional<User> registeredUser = userService.registerUser(userRequestDTO);
		return registeredUser.map(user -> {
							UserResponseDTO result = userService.userResponseDTOFromUserSimple(user);
							URI location = ServletUriComponentsBuilder
									.fromCurrentRequest().path("/{userName}")
									.buildAndExpand(user.getUsername()).toUri();
							return ResponseEntity.created(location).body(result);
		}).orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateOneUser(@PathVariable UUID id,
														 @Valid @RequestBody UserRequestDTO userRequestDTO){
		Optional<User> updatedUser = userService.updateUser(id, userRequestDTO);
		if(updatedUser.isPresent()){
			UserResponseDTO result = userService.userResponseDTOFromUserSimple(updatedUser.get());
			return ResponseEntity.accepted().body(result);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable UUID id){
		Optional<User> result = userService.deleteUserById(id);
		if(result.isPresent()){
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}
}
