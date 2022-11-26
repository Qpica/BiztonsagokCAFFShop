package com.Biztonsagok.CAFFShop.services;

import com.Biztonsagok.CAFFShop.dto.*;
import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.models.PurchaseElement;
import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.models.UserComment;
import com.Biztonsagok.CAFFShop.repositories.CaffPictureRepository;
import com.Biztonsagok.CAFFShop.repositories.PurchaseElementRepository;
import com.Biztonsagok.CAFFShop.repositories.UserCommentRepository;
import com.Biztonsagok.CAFFShop.repositories.UserRepository;
import com.Biztonsagok.CAFFShop.security.service.AuthenticationFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CaffPictureService {
	@Autowired
	private CaffPictureRepository caffPictureRepository;
	@Autowired
	private UserCommentRepository userCommentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PurchaseElementRepository purchaseElementRepository;
	@Autowired
	private final AuthenticationFacade authenticationFacade;

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
		result.setPrice(caffPictureRequestDTO.getPrice());
		userRepository.findByUsername(caffPictureRequestDTO.getOwnerUserName()).ifPresent(result::setOwner);
		return result;
	}

	public CaffPictureResponseDTO caffPictureResponseDTOFromCaffPicture(CaffPicture caffPicture){
		CaffPictureResponseDTO result = new CaffPictureResponseDTO(caffPicture);

		UserResponseDTO owner = new UserResponseDTO(userRepository.findByUsername(authenticationFacade.getCurrentUserFromContext().get().username()));
		if(caffPicture.getUserCommentList() != null) {
			List<UserCommentResponseDTO> ownerComments = caffPicture.getUserCommentList().stream().map(
					userComment -> {
						return new UserCommentResponseDTO(userComment.getComment_value(), userComment.getOwner().getUsername());
					}
			).toList();
			result.setUserCommentList(ownerComments);
		}
		result.setOwner(owner);
		return result;
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

	public Optional<UserComment> addUserCommentToCaffPicture(UUID id, UserCommentRequestDTO userCommentRequestDTO) {
		Optional<CaffPicture> result = caffPictureRepository.findById(id);
		return result.map(caffPicture -> addUserCommentFrom(userCommentRequestDTO, caffPicture));
	}

	private UserComment addUserCommentFrom(UserCommentRequestDTO userCommentRequestDTO, CaffPicture caffPicture) {
		UserComment userComment = new UserComment(userCommentRequestDTO.getComment_value());

		Optional<User> loggedInUser = userRepository.findByUsername("admin");
		loggedInUser.ifPresent(userComment::setOwner);
		userComment.setCaffPicture(caffPicture);
		//caffPicture.getUserCommentList().add(userComment);

		return userCommentRepository.save(userComment);
	}

	public void buyOneCaffPicture(UUID id) throws Exception {
		if(!caffPictureRepository.existsById(id)){
			throw new Exception("Caff Picture does not exist!");
		}
		purchaseElementRepository.save(new PurchaseElement(authenticationFacade.getCurrentUserId().get(), id));
	}

	public void deleteOneCaffPictureById(UUID id) throws Exception {
		if(!caffPictureRepository.existsById(id)){
			throw new Exception("Caff Picture does not exist!");
		}
		caffPictureRepository.deleteById(id);
	}

	public Optional<CaffPicture> updateOne(UUID id, CaffPictureRequestDTO caffPictureRequestDTO) {
		Optional<CaffPicture> picture = caffPictureRepository.findById(id);
		if(picture.isPresent()){
			int oldPrice = picture.get().getPrice();
			picture.get().setPrice(caffPictureRequestDTO.getPrice());
			caffPictureRepository.save(picture.get());

			log.info(MessageFormat.format("[{0}]::[{1}]: Updated CaffPicture({2}) property PRICE[{3} -> {4}]!", LocalDateTime.now().toString(),
					authenticationFacade.getCurrentUserFromContext().get().username(), picture.get().getId(), oldPrice, picture.get().getPrice()));
		}
		return picture;
	}

	public List<CaffPicture> searchByTitle(String title) {
		return caffPictureRepository.findByTitleContainingIgnoreCase(title);
	}
}
