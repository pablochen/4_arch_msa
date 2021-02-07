package com.pablochen.backendVacation.controller;


import com.pablochen.backendVacation.dto.VacationDto;
import com.pablochen.backendVacation.dto.VacationHistDto;
import com.pablochen.backendVacation.entity.Vacation;
import com.pablochen.backendVacation.entity.VacationHist;
import com.pablochen.backendVacation.repository.VacationHistRepository;
import com.pablochen.backendVacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vacation")
public class VacationController {
    @Autowired
    EntityManager em;

    private final VacationRepository vacationRepository;
    private final VacationHistRepository vacationHistRepository;

    /**
     * vacation 영역
     * get, set, add, remove
     */
    @GetMapping("/getVacations")
    public ResponseEntity getVacations (final Pageable pageable){
        ModelMapper modelMapper = new ModelMapper();
        Page<VacationDto> vacations = vacationRepository.findByUseYnEquals("Y", pageable).map(vacation -> modelMapper.map(vacation, VacationDto.class));
        return new ResponseEntity<>(vacations, HttpStatus.OK);
    }

    @PostMapping("/addVacation")
    public ResponseEntity addVacation (@RequestBody VacationDto vacationDto){
        Vacation vacation = new Vacation(vacationDto.getVacationCode(), vacationDto.getVacationName(), vacationDto.getDays());
        vacationRepository.save(vacation);
        return new ResponseEntity<>(vacation, HttpStatus.OK);
    }

    @PutMapping("/removeVacations")
    public ResponseEntity removeVacations (@RequestBody VacationDto vacationDto){
        List<Vacation> vacations = vacationRepository.findAllById(vacationDto.getVacationIds());
        vacations.forEach(vacation -> {
            vacation.setUseYn("N");
        });
        vacationRepository.saveAll(vacations);
        return new ResponseEntity<>(vacations, HttpStatus.OK);
    }

    @PutMapping("/setVacations")
    public ResponseEntity setVacations (@RequestBody List<VacationDto> vacationDtos){
        vacationDtos.forEach(vacationDto -> {
            Vacation vacation = vacationRepository.getOne(vacationDto.getVacationId());
            vacation.setCode(vacationDto.getVacationCode());
            vacation.setName(vacationDto.getVacationName());
            vacation.setDays(vacationDto.getDays());
            vacationRepository.save(vacation);
        });
        return new ResponseEntity<>(vacationDtos, HttpStatus.OK);
    }

    /**
     * vacation 영역
     * get, set, add, remove
     */


    @PostMapping("/addVacationHist")
    public ResponseEntity addVacationHist (@RequestBody VacationHistDto vacationHistDto){
        //String userCode, Vacation vacation, String startDate, String endDate, float days
        Vacation vacation = vacationRepository.getOne(vacationHistDto.getVacationId());
        VacationHist vacationHist = new VacationHist(vacationHistDto.getUserId(), vacation, vacationHistDto.getStartDate(),
                vacationHistDto.getEndDate(),vacationHistDto.getDays());
        vacationHistRepository.save(vacationHist);
        return new ResponseEntity<>(vacationHist, HttpStatus.OK);
    }



    @GetMapping("/getVacationHists")
    public ResponseEntity getVacationHists (@RequestParam(name="userId", required = false) int userId, final Pageable pageable){
        VacationHistDto vacationHistDto = new VacationHistDto();
        vacationHistDto.setUserId(userId);
        Page<VacationHistDto> vacationHists = vacationHistRepository.findHists(vacationHistDto, pageable);
        return new ResponseEntity<>(vacationHists, HttpStatus.OK);
    }

    @PutMapping("/removeVacationHists")
    public ResponseEntity removeVacationHists (@RequestBody VacationHistDto vacationHistDto){
        System.out.println("삭제할 아이디들" +vacationHistDto.getVacationHistIds());
        List<VacationHist> vacationHists = vacationHistRepository.findAllById(vacationHistDto.getVacationHistIds());
        vacationHists.forEach(vacationHist -> {
            vacationHist.setUseYn("N");
        });
        vacationHistRepository.saveAll(vacationHists);
        return new ResponseEntity<>(vacationHists, HttpStatus.OK);
    }

}
