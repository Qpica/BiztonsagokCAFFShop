package com.Biztonsagok.CAFFShop.repositories;

import com.Biztonsagok.CAFFShop.models.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, UUID> {
	Optional<UserComment> findById(UUID id);
}