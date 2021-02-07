package com.pablochen.backendVacation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class VacationDto {
    private int vacationId;
    private String vacationCode;
    private String vacationName;
    private float days;

    private List<Integer> vacationIds;

    @QueryProjection
    public VacationDto(int vacationId, String vacationCode, String vacationName, float days){
        this.vacationId = vacationId;
        this.vacationCode = vacationCode;
        this.vacationName = vacationName;
        this.days = days;
    }

}
