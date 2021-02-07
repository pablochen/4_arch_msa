package com.pablochen.backendUser.repository;


import com.pablochen.backendUser.dto.QUserDto;
import com.pablochen.backendUser.dto.UserDto;
import com.pablochen.backendUser.entity.QDept;
import com.pablochen.backendUser.entity.QPosition;
import com.pablochen.backendUser.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pablochen.backendUser.entity.QDept.dept;
import static com.pablochen.backendUser.entity.QPosition.position;
import static com.pablochen.backendUser.entity.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.SUPPORTS)
    public Page<UserDto> findUsers(UserDto userDto, Pageable pageable){
        QDept qDept = dept;
        QPosition qPosition = position;
        QUser qUser = user;

        List<UserDto> content = queryFactory
                .select(new QUserDto(user.id, user.code, user.name, user.dept.id, dept.code, dept.name,
                        user.position.id, position.code, position.name, user.totalVacCnt, user.remainVacCnt, user.joinDate))
                .from(user)
                .leftJoin(dept)
                    .on(user.dept.id.eq(dept.id))
                .leftJoin(position)
                    .on(user.position.id.eq(position.id))
                .where(
                        userIdEq(userDto.getUserId()),
                        userAlive()
                )
                .orderBy(user.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(new QUserDto(user.id, user.code, user.name, user.dept.id, dept.code, dept.name,
                        user.position.id, position.code, position.name, user.totalVacCnt, user.remainVacCnt, user.joinDate))
                .from(user)
                .leftJoin(dept)
                    .on(user.dept.id.eq(dept.id))
                .leftJoin(position)
                    .on(user.position.id.eq(position.id))
                .where(
                        userIdEq(userDto.getUserId()),
                        userAlive()
                )
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    public BooleanExpression userIdEq(Integer userId){
        return (userId != null && userId != 0) ? user.id.eq(userId) : null;
    }

    public BooleanExpression userAlive(){
        return user.useYn.eq("Y");
    }


}
