package com.Biztonsagok.CAFFShop.controllers;

import com.Biztonsagok.CAFFShop.dto.*;
import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.services.CaffPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/caffPictures")
public class CaffPictureController {
	@Autowired
	private CaffPictureService caffPictureService;

	@GetMapping
	public ResponseEntity<CollectionModel<CaffPictureResponseDTO>> getAllCaffPicture(){
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

	@GetMapping("/{id}")
	public ResponseEntity<CaffPictureResponseDTO> getOneCaffPicture(@PathVariable UUID id){
		Optional<CaffPictureResponseDTO> result = caffPictureService.getCaffPictureResponseDTO(id);
		if(result.isPresent()){
			result.get().add(linkTo(methodOn(CaffPictureController.class).getOneCaffPicture(id)).withSelfRel());
			result.get().add(linkTo(methodOn(CaffPictureController.class).getAllCaffPicture()).withRel(IanaLinkRelations.COLLECTION));
		}
		return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/data")
	public ResponseEntity<CaffPictureDataResponseDTO> getOneCaffPictureData(@PathVariable UUID id){
		Optional<CaffPictureDataResponseDTO> result = caffPictureService.getCaffPictureDataResponseDTO(id);
		return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CaffPictureResponseDTO> addCaffPicture(
			@Valid @ModelAttribute @NotNull CaffPictureRequestDTO caffPictureRequestDTO
			){
		Optional<CaffPicture> storedCaffPicture;
		try {
			storedCaffPicture = caffPictureService.storeFile(
					caffPictureService.caffPictureFromCaffPictureRequestDTO(caffPictureRequestDTO)
			);
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

	@PostMapping("/{id}/comments")
	public ResponseEntity<Void> addUserCommentToCaffPicture(
			@PathVariable UUID id,
			@Valid @RequestBody UserCommentRequestDTO userCommentRequestDTO){
		Optional<CaffPicture> caffPictureWithAddedComment = caffPictureService.addUserCommentToCaffPicture(id,
				userCommentRequestDTO);
		if(caffPictureWithAddedComment.isPresent()){
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
