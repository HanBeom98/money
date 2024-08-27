package com.sparta.project.Settlement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findAllBySettlementDate(LocalDate settlementDate);
    List<Settlement> findAllBySettlementDateBetween(LocalDate startDate, LocalDate endDate);
}
