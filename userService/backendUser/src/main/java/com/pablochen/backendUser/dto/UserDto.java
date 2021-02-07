package com.pablochen.backendUser.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private int userId;
    private String userCode;
    private String userName;

    private int deptId;
    private String deptCode;
    private String deptName;

    private int positionId;
    private String positionCode;
    private String positionName;

    private float totalVacCnt;
    private float remainVacCnt;
    private String joinDate;

    private List<Integer> userIds;
    private float days;

    @QueryProjection
    public UserDto(int userId, String userCode, String userName,
                   int deptId, String deptCode, String deptName,
                   int positionId, String positionCode, String positionName,
                   float totalVacCnt, float remainVacCnt, String joinDate){
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.deptId = deptId;
        this.deptCode = deptCode;
        this.deptName = deptName;
        this.positionId = positionId;
        this.positionCode = positionCode;
        this.positionName = positionName;
        this.totalVacCnt = totalVacCnt;
        this.remainVacCnt = remainVacCnt;
        this.joinDate = joinDate;
    }

}
