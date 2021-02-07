package com.pablochen.backendUser.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PositionDto {
    private int positionId;
    private String positionCode;
    private String positionName;

    private List<Integer> positionIds;

    @QueryProjection
    public PositionDto(int positionId, String positionCode, String positionName){
        this.positionCode = positionCode;
        this.positionName = positionName;
    }

}
