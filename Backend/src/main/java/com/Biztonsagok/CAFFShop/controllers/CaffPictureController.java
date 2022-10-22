package com.Biztonsagok.CAFFShop.controllers;

import com.Biztonsagok.CAFFShop.dto.CaffPictureDataResponseDTO;
import com.Biztonsagok.CAFFShop.dto.CaffPictureRequestDTO;
import com.Biztonsagok.CAFFShop.dto.CaffPictureResponseDTO;
import com.Biztonsagok.CAFFShop.dto.UserResponseDTO;
import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.services.CaffPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/caffPictures")
public class CaffPictureController {
	@Autowired
	private CaffPictureService caffPictureService;

	@GetMapping
	public ResponseEntity<List<CaffPictureResponseDTO>> getAllCaffPicture(){
		List<CaffPictureResponseDTO> responseDTOList = caffPictureService.getAllCaffPicture().stream()
				.map(
					caffPicture -> caffPictureService.caffPictureResponseDTOFromCaffPicture(caffPicture)
				).collect(Collectors.toList());
		return ResponseEntity.ok(responseDTOList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CaffPictureResponseDTO> getOneCaffPicture(@PathVariable UUID id){
		Optional<CaffPictureResponseDTO> result = caffPictureService.getCaffPictureResponseDTO(id);
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
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(caffPicture.getId()).toUri();
			return ResponseEntity.created(location).body(result);
		}).orElseGet(() -> ResponseEntity.badRequest().build());
	}
}
