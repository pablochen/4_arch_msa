package com.pablochen.backendUser.repository;


import com.pablochen.backendUser.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer>{
    Page<Position> findByUseYnEquals(String useYn, Pageable pageable);
}
