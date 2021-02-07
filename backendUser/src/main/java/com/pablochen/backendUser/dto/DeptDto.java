package com.pablochen.backendUser.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DeptDto {
    private int deptId;
    private String deptCode;
    private String deptName;

    private List<Integer> deptIds;

    @QueryProjection
    public DeptDto(int deptId, String deptCode, String deptName){
        this.deptId = deptId;
        this.deptCode = deptCode;
        this.deptName = deptName;
    }
}
