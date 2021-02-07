package com.pablochen.backendVacation.repository;


import com.pablochen.backendVacation.dto.QVacationHistDto;
import com.pablochen.backendVacation.dto.VacationHistDto;
import com.pablochen.backendVacation.entity.QVacation;
import com.pablochen.backendVacation.entity.QVacationHist;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pablochen.backendVacation.entity.QVacation.vacation;
import static com.pablochen.backendVacation.entity.QVacationHist.vacationHist;

public class VacationHistRepositoryImpl implements VacationHistRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public VacationHistRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.SUPPORTS)
    public Page<VacationHistDto> findHists(VacationHistDto vacationHistDto, Pageable pageable) {

        QVacation qVacation = vacation;
        QVacationHist qVacationHist = vacationHist;

        List<VacationHistDto> content = queryFactory
                .select(new QVacationHistDto(vacationHist.id, vacationHist.userId,
                        vacationHist.vacation.id, vacation.code, vacation.name,
                        vacationHist.startDate, vacationHist.endDate, vacationHist.days, vacationHist.useYn))
                .from(vacationHist)
                .leftJoin(vacation)
                    .on(vacationHist.vacation.id.eq(vacation.id))
                .where(
                        userIdEq(vacationHistDto.getUserId())
                )
                .orderBy(vacationHist.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(new QVacationHistDto(vacationHist.id, vacationHist.userId,
                        vacationHist.vacation.id, vacation.code, vacation.name,
                        vacationHist.startDate, vacationHist.endDate, vacationHist.days, vacationHist.useYn))
                .from(vacationHist)
                .leftJoin(vacation)
                    .on(vacationHist.vacation.id.eq(vacation.id))
               .where(
                        userIdEq(vacationHistDto.getUserId())
                )
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    public BooleanExpression userIdEq(Integer userId){
        return (userId!=null && userId!=0) ? vacationHist.userId.eq(userId) : null;
    }

}
