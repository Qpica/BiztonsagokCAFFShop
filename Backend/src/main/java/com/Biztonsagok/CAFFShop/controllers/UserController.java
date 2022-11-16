package com.Biztonsagok.CAFFShop.controllers;

import com.Biztonsagok.CAFFShop.dto.UserRequestDTO;
import com.Biztonsagok.CAFFShop.dto.UserResponseDTO;
import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.security.service.AuthenticationFacade;
import com.Biztonsagok.CAFFShop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationFacade authenticationFacade;

	@GetMapping
	public ResponseEntity<CollectionModel<UserResponseDTO>> getAllUsers() {

		log.info(MessageFormat.format("[{0}]::[{1}]: Retrieved all Users!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username()));

		List<UserResponseDTO> responseDTOList = userService.getAllUsers().stream()
				.map(
						user -> {
							UserResponseDTO result = userService.userResponseDTOFromUserSimple(user);
							result.add(linkTo(methodOn(UserController.class).getUserByUserName(result.getUserName())).withSelfRel());
							return result;
						}
				).collect(Collectors.toList());
		CollectionModel<UserResponseDTO> collectionModel = CollectionModel.of(responseDTOList);
		collectionModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/{userName}")
	public ResponseEntity<UserResponseDTO> getUserByUserName(@PathVariable String userName){
		Optional<UserResponseDTO> result = userService.getUserResponseDTOByUserName(userName);
		String logMessage = result.isPresent() ? result.get().getUserName() : "User not found!";
		log.info(MessageFormat.format("[{0}]::[{1}]: Retrieved User({2})!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username(), logMessage));

		if(result.isPresent()){
			result.get().add(linkTo(methodOn(UserController.class).getUserByUserName(userName)).withSelfRel());
			result.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel(IanaLinkRelations.COLLECTION));
		}
		return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO){

		log.info(MessageFormat.format("[{0}]::[{1}]: Registered User({2})!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username(), userRequestDTO.getUsername()));

		Optional<User> registeredUser = userService.registerUser(userRequestDTO);
		return registeredUser.map(user -> {
							UserResponseDTO result = userService.userResponseDTOFromUserSimple(user);
							URI location = ServletUriComponentsBuilder
									.fromCurrentRequest().path("/{userName}")
									.buildAndExpand(user.getUsername()).toUri();
							return ResponseEntity.created(location).body(result);
		}).orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("/{userName}")
	@PreAuthorize("@authenticationService.hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<UserResponseDTO> updateOneUser(@PathVariable String userName,
														 @Valid @RequestBody UserRequestDTO userRequestDTO){
		Optional<User> updatedUser = userService.updateUser(userName, userRequestDTO);

		log.info(MessageFormat.format("[{0}]::[{1}]: Updated User({2})!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username(), Objects.requireNonNullElse(updatedUser.get().getUsername(), "User not found!")));

		if(updatedUser.isPresent()){
			UserResponseDTO result = userService.userResponseDTOFromUserSimple(updatedUser.get());
			result.add(linkTo(methodOn(UserController.class).getUserByUserName(result.getUserName())).withSelfRel());
			result.add().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel(IanaLinkRelations.COLLECTION));
			return ResponseEntity.accepted().body(result);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{userName}")
	@PreAuthorize("@authenticationService.hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable String userName){
		Optional<User> result = userService.deleteUserById(userName);
		if(result.isPresent()){

			log.info(MessageFormat.format("[{0}]::[{1}]: Deleted User({2})!", LocalDateTime.now().toString(),
					authenticationFacade.getCurrentUserFromContext().get().username(), Objects.requireNonNullElse(result.get().getUsername(), "User not found!")));

			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}
}
