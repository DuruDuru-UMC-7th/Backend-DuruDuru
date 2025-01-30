package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
