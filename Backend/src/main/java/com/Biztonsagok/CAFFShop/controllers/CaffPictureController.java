package com.Biztonsagok.CAFFShop.controllers;

import com.Biztonsagok.CAFFShop.dto.CaffPictureRequestDTO;
import com.Biztonsagok.CAFFShop.dto.CaffPictureResponseDTO;
import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.services.CaffPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
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

	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<CaffPictureResponseDTO> addCaffPicture(
			@Valid @ModelAttribute @NotNull CaffPictureRequestDTO caffPictureRequestDTO
			){
		CaffPicture result;
		try {
			result = caffPictureService.storeFile(
					caffPictureService.caffPictureFromCaffPictureRequestDTO(caffPictureRequestDTO)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.ok(caffPictureService.caffPictureResponseDTOFromCaffPicture(result));
	}
}
