package com.pablochen.backendVacation.repository;

import com.pablochen.backendVacation.dto.VacationHistDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VacationHistRepositoryCustom {
    public Page<VacationHistDto> findHists(VacationHistDto vacationHistDto, Pageable pageable);
}
