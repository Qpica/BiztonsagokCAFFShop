package com.Biztonsagok.CAFFShop.controllers;

import com.Biztonsagok.CAFFShop.dto.CaffPictureResponseDTO;
import com.Biztonsagok.CAFFShop.services.CaffPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/caffPictures")
public class CaffPictureController {
	@Autowired
	private CaffPictureService caffPictureService;

	@GetMapping
	public ResponseEntity<List<CaffPictureResponseDTO>> getAllCaffPicture(){
		List<CaffPictureResponseDTO> responseDTOList = caffPictureService.getAllCaffPicture().stream().map(caffPicture ->
				{
					return new CaffPictureResponseDTO(
							caffPicture.getTitle(),
							caffPicture.getTitle(),
							caffPicture.getUserCommentList(),
							caffPicture.getOwner());
				}).collect(Collectors.toList());
		return ResponseEntity.ok(responseDTOList);
	}


}
