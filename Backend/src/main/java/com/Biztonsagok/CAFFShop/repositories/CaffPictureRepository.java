package com.Biztonsagok.CAFFShop.repositories;

import com.Biztonsagok.CAFFShop.models.CaffPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CaffPictureRepository extends JpaRepository<CaffPicture, UUID> {
	List<CaffPicture> findByTitleContainingIgnoreCase(String title);
}
