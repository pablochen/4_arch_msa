package com.pablochen.backendUser.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(name="USER_SEQ_GEN", sequenceName="USER_SEQ", initialValue=1, allocationSize=1)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN")
    private int id;

    @Column(name = "USER_CODE", columnDefinition = "VARCHAR(5)", unique = true)
    private String code;

    @Column(name = "USER_NAME", columnDefinition = "VARCHAR(30)")
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id")
    private Position position;

    @Column(columnDefinition = "FLOAT")
    private float totalVacCnt;

    @Column(columnDefinition = "FLOAT")
    private float remainVacCnt;

    @Column(columnDefinition = "VARCHAR(8)")
    private String joinDate;

    @Column(columnDefinition = "VARCHAR(1)")
    private String useYn;

    public User(String userCode, String userName, Dept dept, Position position, String joinDate){
        this.code = userCode;
        this.name = userName;
        this.dept = dept;
        this.position = position;
        this.totalVacCnt = 15;
        this.remainVacCnt = 15;
        this.joinDate = joinDate;
        this.useYn = "Y";
    }

    public User(String userCode) {
        this.code = userCode;
    }

    @Override
    public String  toString() {
        String content = "code : " + this.code +
                "name : " + this.name +
                "code : " + this.code +
                "deptCode : " + this.dept.getName() +
                "positionCode : " + this.position.getName() +
                "totalVacCnt : " + this.totalVacCnt +
                "remainVacCnt : " + this.remainVacCnt +
                "joinDate : " + this.joinDate;
        return content;
    }

}
