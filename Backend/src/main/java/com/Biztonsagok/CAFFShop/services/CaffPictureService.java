package com.Biztonsagok.CAFFShop.services;

import com.Biztonsagok.CAFFShop.dto.CaffPictureDataResponseDTO;
import com.Biztonsagok.CAFFShop.dto.CaffPictureRequestDTO;
import com.Biztonsagok.CAFFShop.dto.CaffPictureResponseDTO;
import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.repositories.CaffPictureRepository;
import com.Biztonsagok.CAFFShop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.*;

@Service
public class CaffPictureService {
	@Autowired
	private CaffPictureRepository caffPictureRepository;

	@Autowired
	private UserRepository userRepository;

	public Optional<CaffPicture> storeFile(CaffPicture caffPicture/*, MultipartFile caffPictureFile*/) throws IOException {
		//String fileName = StringUtils.cleanPath(Objects.requireNonNull(caffPictureFile.getOriginalFilename()));
		//caffPicture.setCaffPictureData(caffPictureFile.getBytes());
		return Optional.of(caffPictureRepository.save(caffPicture));
	}

	public Optional<CaffPicture> getCaffPicture(UUID id){
		return caffPictureRepository.findById(id);
	}

	public List<CaffPicture> getAllCaffPicture(){
		return caffPictureRepository.findAll();
	}

	public CaffPicture caffPictureFromCaffPictureRequestDTO(CaffPictureRequestDTO caffPictureRequestDTO)
			throws IOException {
		CaffPicture result = new CaffPicture();
		result.setTitle(caffPictureRequestDTO.getTitle());
		result.setDescription(caffPictureRequestDTO.getDescription());
		result.setCaffPictureData(caffPictureRequestDTO.getCaffFile().getBytes());
		userRepository.findByUsername(caffPictureRequestDTO.getOwnerUserName()).ifPresent(result::setOwner);
		return result;
	}

	public CaffPictureResponseDTO caffPictureResponseDTOFromCaffPicture(CaffPicture caffPicture){
		return new CaffPictureResponseDTO(caffPicture);
	}

	public CaffPictureDataResponseDTO caffPictureResponseDataDTOFromCaffPicture(CaffPicture caffPicture){
		return new CaffPictureDataResponseDTO(caffPicture);
	}

	public Optional<CaffPictureResponseDTO> getCaffPictureResponseDTO(UUID id) {
		Optional<CaffPicture> result = caffPictureRepository.findById(id);
		return result.map(this::caffPictureResponseDTOFromCaffPicture);
	}

	public Optional<CaffPictureDataResponseDTO> getCaffPictureDataResponseDTO(UUID id) {
		Optional<CaffPicture> result = caffPictureRepository.findById(id);
		return result.map(this::caffPictureResponseDataDTOFromCaffPicture);
	}
}
