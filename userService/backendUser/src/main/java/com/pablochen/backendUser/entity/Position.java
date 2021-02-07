package com.pablochen.backendUser.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(name="POSITION_SEQ_GEN", sequenceName="POSITION_SEQ", initialValue=1, allocationSize=1)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Position {
    @Id
    @Column(name = "POSITION_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="POSITION_SEQ_GEN")
    private int id;

    @Column(name = "POSITION_CODE", columnDefinition = "VARCHAR(3)", unique = true)
    private String code;

    @Column(name = "POSITION_NAME", columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(columnDefinition = "VARCHAR(1)")
    private String useYn;

    public Position(String positionCode, String positionName){
        this.code = positionCode;
        this.name = positionName;
        this.useYn = "Y";
    }

    public Position(String positionCode){
        this.code = positionCode;
    }
}
