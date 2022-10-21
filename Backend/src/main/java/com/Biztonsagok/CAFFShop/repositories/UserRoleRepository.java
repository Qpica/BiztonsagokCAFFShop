package com.Biztonsagok.CAFFShop.repositories;

import com.Biztonsagok.CAFFShop.models.UserRole;
import com.Biztonsagok.CAFFShop.models.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
	Optional<UserRole> findByRoleName(UserRoleType roleName);
}