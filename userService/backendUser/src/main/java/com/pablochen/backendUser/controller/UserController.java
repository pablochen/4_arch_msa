package com.pablochen.backendUser.controller;

import com.pablochen.backendUser.dto.DeptDto;
import com.pablochen.backendUser.dto.PositionDto;
import com.pablochen.backendUser.dto.UserDto;
import com.pablochen.backendUser.entity.Dept;
import com.pablochen.backendUser.entity.Position;
import com.pablochen.backendUser.entity.User;
import com.pablochen.backendUser.repository.DeptRepository;
import com.pablochen.backendUser.repository.PositionRepository;
import com.pablochen.backendUser.repository.UserRepository;
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
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    EntityManager em;

    private final UserRepository userRepository;
    private final DeptRepository deptRepository;
    private final PositionRepository positionRepository;

    /**
     * dept 영역
     * get, set, add, remove
     */
    @GetMapping("/getDepts")
    public ResponseEntity getDepts (final Pageable pageable){
        ModelMapper modelMapper = new ModelMapper();
        Page<DeptDto> depts = deptRepository.findByUseYnEquals("Y", pageable).map(dept -> modelMapper.map(dept, DeptDto.class));
        return new ResponseEntity<>(depts, HttpStatus.OK);
    }

    @PostMapping("/addDept")
    public ResponseEntity addDept (@RequestBody DeptDto deptDto){
        Dept dept = new Dept(deptDto.getDeptCode(), deptDto.getDeptName());
        deptRepository.save(dept);
        return new ResponseEntity<>(dept, HttpStatus.OK);
    }

    @PutMapping("/removeDepts")
    public ResponseEntity removeDepts (@RequestBody DeptDto deptDto){
        List<Dept> depts = deptRepository.findAllById(deptDto.getDeptIds());
        depts.forEach(dept -> {
            dept.setUseYn("N");
        });
        deptRepository.saveAll(depts);
        return new ResponseEntity<>(depts, HttpStatus.OK);
    }

    @PutMapping("/setDepts")
    public ResponseEntity setDepts (@RequestBody List<DeptDto> deptDtos){
       deptDtos.forEach(deptDto -> {
            Dept dept = deptRepository.getOne(deptDto.getDeptId());
            dept.setCode(deptDto.getDeptCode());
            dept.setName(deptDto.getDeptName());
            deptRepository.save(dept);
        });
        return new ResponseEntity<>(deptDtos, HttpStatus.OK);
    }



    /**
     * position 영역
     * get, set, add, remove
     */
    @GetMapping("/getPositions")
    public ResponseEntity getPositions (final Pageable pageable){
        ModelMapper modelMapper = new ModelMapper();
        Page<PositionDto> positions = positionRepository.findByUseYnEquals("Y", pageable).map(position -> modelMapper.map(position, PositionDto.class));
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @PostMapping("/addPosition")
    public ResponseEntity addPosition (@RequestBody PositionDto positionDto){
        Position position = new Position(positionDto.getPositionCode(), positionDto.getPositionName());
        positionRepository.save(position);
        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @PutMapping("/removePositions")
    public ResponseEntity removePositions (@RequestBody PositionDto positionDto){
        List<Position> positions = positionRepository.findAllById(positionDto.getPositionIds());
        positions.forEach(position -> {
            position.setUseYn("N");
        });
        positionRepository.saveAll(positions);
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @PutMapping("/setPositions")
    public ResponseEntity setPositions (@RequestBody List<PositionDto> positionDtos){
        positionDtos.forEach(positionDto -> {
            Position position = positionRepository.getOne(positionDto.getPositionId());
            position.setCode(positionDto.getPositionCode());
            position.setName(positionDto.getPositionName());
            positionRepository.save(position);
        });
        return new ResponseEntity<>(positionDtos, HttpStatus.OK);
    }


    /**
     * user 영역
     * get, set, add, remove
     */
    @GetMapping("/getUsers")
    public ResponseEntity getUsers (final Pageable pageable){
        UserDto userDto = new UserDto();
        Page<UserDto> userList = userRepository.findUsers(userDto, pageable);
        System.out.println(userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser (@RequestBody UserDto userDto){
        Dept dept = em.getReference(Dept.class, userDto.getDeptId());
        Position position = em.getReference(Position.class, userDto.getPositionId());
        User user = new User(userDto.getUserCode(), userDto.getUserName(), dept, position, userDto.getJoinDate());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/removeUsers")
    public ResponseEntity removeUsers (@RequestBody UserDto userDto){
        List<User> users = userRepository.findAllById(userDto.getUserIds());
        users.forEach(user -> {
            user.setUseYn("N");
        });
        userRepository.saveAll(users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/setUsers")
    public ResponseEntity setUsers (@RequestBody List<UserDto> userDtos){
        userDtos.forEach(userDto -> {
            User user = userRepository.getOne(userDto.getUserId());
            user.setCode(userDto.getUserCode());
            user.setName(userDto.getUserName());
            user.setDept(deptRepository.getOne(userDto.getDeptId()));
            user.setPosition(positionRepository.getOne(userDto.getPositionId()));
            user.setTotalVacCnt(userDto.getTotalVacCnt());
            user.setRemainVacCnt(userDto.getRemainVacCnt());
            user.setJoinDate(userDto.getJoinDate());
            userRepository.save(user);
        });
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PutMapping("/addUserDays")
    public ResponseEntity setUserDays (@RequestBody UserDto userDto){
        User user = userRepository.getOne(userDto.getUserId());
        user.setRemainVacCnt(user.getRemainVacCnt() + userDto.getDays());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
