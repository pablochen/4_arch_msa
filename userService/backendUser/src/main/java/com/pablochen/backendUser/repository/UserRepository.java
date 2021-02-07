package com.pablochen.backendUser.repository;


import com.pablochen.backendUser.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    Page<User> findByUseYnEquals(String useYn, Pageable pageable);
    List<User> findAllByIdIn(List<Integer> ids);
    User findByCodeEquals(String UserCode);
}
