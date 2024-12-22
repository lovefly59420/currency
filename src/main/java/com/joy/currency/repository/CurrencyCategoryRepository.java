package com.joy.currency.repository;

import com.joy.currency.entity.CurrencyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyCategoryRepository extends JpaRepository<CurrencyCategory, Long> {
    Optional<CurrencyCategory> findByCurrency(String Currency);
}
