package com.pablochen.backendVacation.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(name="VACATION_SEQ_GEN", sequenceName="VACATION_SEQ", initialValue=1, allocationSize=1)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Vacation {
    @Id
    @Column(name = "VACATION_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="VACATION_SEQ_GEN")
    private int id;

    @Column(name = "VACATION_CODE", columnDefinition = "VARCHAR(3)", unique = true)
    private String code;

    @Column(name = "VACATION_NAME", columnDefinition = "VARCHAR(30)")
    private String name;

    private float days;

    @Column(columnDefinition = "VARCHAR(1)")
    private String useYn;

    public Vacation(String vacationCode, String vacationName, float days){
        this.code = vacationCode;
        this.name = vacationName;
        this.days = days;
        this.useYn = "Y";
    }

    public Vacation(String vacationCode){
        this.code = vacationCode;
    }
}
