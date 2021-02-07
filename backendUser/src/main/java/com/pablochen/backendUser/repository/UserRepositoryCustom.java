package com.pablochen.backendUser.repository;


import com.pablochen.backendUser.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom{
    public Page<UserDto> findUsers(UserDto userDto, Pageable pageable);
}
