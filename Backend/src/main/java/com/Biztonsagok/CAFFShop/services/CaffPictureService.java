package com.Biztonsagok.CAFFShop.services;

import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.repositories.CaffPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.*;

@Service
public class CaffPictureService {
	@Autowired
	private CaffPictureRepository caffPictureRepository;

	public CaffPicture storeFile(CaffPicture caffPicture, MultipartFile caffPictureFile) throws IOException {
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(caffPictureFile.getOriginalFilename()));
		caffPicture.setCaffPictureData(caffPictureFile.getBytes());
		return caffPictureRepository.save(caffPicture);
	}

	public Optional<CaffPicture> getCaffPicture(UUID id){
		return caffPictureRepository.findById(id);
	}

	public List<CaffPicture> getAllCaffPicture(){
		return caffPictureRepository.findAll();
	}
}
