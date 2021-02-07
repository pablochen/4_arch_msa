package com.pablochen.backendUser.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(name="DEPT_SEQ_GEN", sequenceName="DEPT_SEQ", initialValue=1, allocationSize=1)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Dept {
    @Id
    @Column(name = "DEPT_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DEPT_SEQ_GEN")
    private int id;

    @Column(name = "DEPT_CODE", columnDefinition = "VARCHAR(5)", unique = true)
    private String code;

    @Column(name = "DEPT_NAME", columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(columnDefinition = "VARCHAR(1)")
    private String useYn;

    public Dept(String deptCode, String deptName){
        this.code = deptCode;
        this.name = deptName;
        this.useYn = "Y";
    }

    public Dept(String deptCode){
        this.code = deptCode;
    }
}
