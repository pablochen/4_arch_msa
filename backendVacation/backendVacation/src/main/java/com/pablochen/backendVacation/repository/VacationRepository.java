package com.pablochen.backendVacation.repository;

import com.pablochen.backendVacation.entity.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Integer>{
    Page<Vacation> findByUseYnEquals(String useYn, Pageable pageable);
}
