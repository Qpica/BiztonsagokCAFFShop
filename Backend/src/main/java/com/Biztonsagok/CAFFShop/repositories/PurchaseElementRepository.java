package com.Biztonsagok.CAFFShop.repositories;

import com.Biztonsagok.CAFFShop.models.PurchaseElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseElementRepository extends JpaRepository<PurchaseElement, UUID> {
}
