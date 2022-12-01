package com.Biztonsagok.CAFFShop.controllers;

import com.Biztonsagok.CAFFShop.dto.*;
import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.models.UserComment;
import com.Biztonsagok.CAFFShop.security.service.AuthenticationFacade;
import com.Biztonsagok.CAFFShop.services.CaffPictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Slf4j
@RestController
@RequestMapping("/api/caffPictures")
public class CaffPictureController {
	@Autowired
	private CaffPictureService caffPictureService;
	@Autowired
	private AuthenticationFacade authenticationFacade;

	@GetMapping
	public ResponseEntity<CollectionModel<CaffPictureResponseDTO>> getAllCaffPicture(){

		log.info(MessageFormat.format("[{0}]::[{1}]: Retrieved all CaffPictures!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username()));

		List<CaffPictureResponseDTO> responseDTOList = caffPictureService.getAllCaffPicture().stream()
				.map(
					caffPicture -> {
						CaffPictureResponseDTO result = caffPictureService.caffPictureResponseDTOFromCaffPicture(caffPicture);
						result.add(linkTo(methodOn(CaffPictureController.class).getOneCaffPicture(caffPicture.getId())).withSelfRel());
						return result;
					}
				).collect(Collectors.toList());
		CollectionModel<CaffPictureResponseDTO> collectionModel = CollectionModel.of(responseDTOList);
		collectionModel.add(linkTo(methodOn(CaffPictureController.class).getAllCaffPicture()).withSelfRel());
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/search")
	public ResponseEntity<CollectionModel<CaffPictureResponseDTO>> searchCaffPictureByTitle(@RequestParam("Title") String title){
		log.info(MessageFormat.format("[{0}]::[{1}]: Searched for CaffPictures by Param:[{2}]!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username(), title));

		List<CaffPictureResponseDTO> responseDTOList = caffPictureService.searchByTitle(title).stream()
				.map(
						caffPicture -> {
							CaffPictureResponseDTO result = caffPictureService.caffPictureResponseDTOFromCaffPicture(caffPicture);
							result.add(linkTo(methodOn(CaffPictureController.class).getOneCaffPicture(caffPicture.getId())).withSelfRel());
							return result;
						}
				).collect(Collectors.toList());
		CollectionModel<CaffPictureResponseDTO> collectionModel = CollectionModel.of(responseDTOList);
		collectionModel.add(linkTo(methodOn(CaffPictureController.class).getAllCaffPicture()).withSelfRel());
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CaffPictureResponseDTO> getOneCaffPicture(@PathVariable UUID id){

		log.info(MessageFormat.format("[{0}]::[{1}]: Retrieved CaffPicture({2})!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username(), id));

		Optional<CaffPictureResponseDTO> result = caffPictureService.getCaffPictureResponseDTO(id);
		if(result.isPresent()){
			result.get().add(linkTo(methodOn(CaffPictureController.class).getOneCaffPicture(id)).withSelfRel());
			result.get().add(linkTo(methodOn(CaffPictureController.class).getAllCaffPicture()).withRel(IanaLinkRelations.COLLECTION));
		}
		return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/data")
	public ResponseEntity<Resource> getOneCaffPictureData(@PathVariable UUID id){

		log.info(MessageFormat.format("[{0}]::[{1}]: Downloaded CaffPicture({2})!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username(), id));

		Optional<byte[]> result = caffPictureService.getCaffPictureData(id);
		if (result.isPresent()){
			ByteArrayResource resource = new ByteArrayResource(result.get());
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.contentLength(resource.contentLength())
					.header(HttpHeaders.CONTENT_DISPOSITION,
							ContentDisposition.attachment()
									.filename("CAFF")
									.build().toString())
					.body(resource);
		}
		else {
			return ResponseEntity.noContent().build();
		}
		/*
		Optional<CaffPictureDataResponseDTO> result = caffPictureService.getCaffPictureDataResponseDTO(id);
		return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
		 */
	}

	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CaffPictureResponseDTO> addCaffPicture(
			@Valid @ModelAttribute @NotNull CaffPictureRequestDTO caffPictureRequestDTO
			){

		log.info(MessageFormat.format("[{0}]::[{1}]: Added CaffPicture!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username()));

		Optional<CaffPicture> storedCaffPicture;
		try {
			storedCaffPicture = caffPictureService.storeFile(
					caffPictureService.caffPictureFromCaffPictureRequestDTO(caffPictureRequestDTO)
			);

			log.info(MessageFormat.format("[{0}]::[{1}]: Saved CaffPicture({2})!", LocalDateTime.now().toString(),
					authenticationFacade.getCurrentUserFromContext().get().username(), storedCaffPicture.get().getId()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return storedCaffPicture.map(caffPicture -> {
			CaffPictureResponseDTO result = caffPictureService.caffPictureResponseDTOFromCaffPicture(caffPicture);
			final Link link = WebMvcLinkBuilder.linkTo(methodOn(CaffPictureController.class)
					.getOneCaffPicture(caffPicture.getId())).withSelfRel();
			result.add(link);
			return ResponseEntity.created(link.toUri()).body(result);
		}).orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("/{id}")
	@PreAuthorize("@authenticationService.hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<CaffPictureResponseDTO> updateOneCaffPicture(@PathVariable UUID id,
																	   CaffPictureRequestDTO caffPictureRequestDTO){
		Optional<CaffPicture> caffPicture = caffPictureService.updateOne(id, caffPictureRequestDTO);
		if(caffPicture.isPresent()){
			CaffPictureResponseDTO caffPictureResponseDTO = caffPictureService.caffPictureResponseDTOFromCaffPicture(caffPicture.get());

			log.info(MessageFormat.format("[{0}]::[{1}]: Updated CaffPicture({2})!", LocalDateTime.now().toString(),
					authenticationFacade.getCurrentUserFromContext().get().username(), caffPicture.get().getId()));

			caffPictureResponseDTO.add(linkTo(methodOn(CaffPictureController.class).getOneCaffPicture(id)).withSelfRel());
			caffPictureResponseDTO.add(linkTo(methodOn(CaffPictureController.class).getAllCaffPicture()).withRel(IanaLinkRelations.COLLECTION));

			return ResponseEntity.ok(caffPictureResponseDTO);
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/{id}/comments")
	public ResponseEntity<Void> addUserCommentToCaffPicture(
			@PathVariable UUID id,
			@Valid @RequestBody UserCommentRequestDTO userCommentRequestDTO){

		log.info(MessageFormat.format("[{0}]::[{1}]: Added Comment CaffPicture({2})!", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username(), id));

		Optional<UserComment> addedUserComment = caffPictureService.addUserCommentToCaffPicture(id,
				userCommentRequestDTO);
		if(addedUserComment.isPresent()){

			log.info(MessageFormat.format("[{0}]::[{1}]: Saved Comment({2}) for CaffPicture({3})!", LocalDateTime.now().toString(),
					authenticationFacade.getCurrentUserFromContext().get().username(), addedUserComment.get().getId(), id));

			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{id}/buy")
	public ResponseEntity<Void> buyOneCaffPicture(@PathVariable UUID id){
		try {
			caffPictureService.buyOneCaffPicture(id);
		}
		catch (Exception ignored){
			return ResponseEntity.badRequest().build();
		}

		log.info(MessageFormat.format("[{0}]::[{1}]: Bought CaffPicture({2})", LocalDateTime.now().toString(),
				authenticationFacade.getCurrentUserFromContext().get().username(), id));

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("@authenticationService.hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<Void> deleteOneCaffPictureById(@PathVariable UUID id){
		try {
			caffPictureService.deleteOneCaffPictureById(id);
		}
		catch (Exception ignored){
			return ResponseEntity.badRequest().build();
		}

		log.info(MessageFormat.format("[{0}]::[{1}]: Deleted CaffPicture({2})!", LocalDateTime.now().toString(), authenticationFacade.getCurrentUserFromContext().get().username(), id));

		return ResponseEntity.noContent().build();
	}
}
