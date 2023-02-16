package com.diet.second_project_diet.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import org.springframework.data.repository.query.Param;

public interface DayFoodCompleteRepository extends JpaRepository<DayFoodCompleteEntity, Long>{
    // public Integer countByMemberAndDfcDate(MemberInfoEntity member, LocalDate dfcDate);
    public Integer countByMemberAndDfcDate(MemberInfoEntity member, LocalDate dfcDate);

    // public DayFoodCompleteEntity deleteByMemberAndDfcDate(MemberInfoEntity member, LocalDate dfcDate);
    public DayFoodCompleteEntity deleteByMemberAndDfcDate(MemberInfoEntity member, LocalDate dfcDate);

    @Query(value = "select * from day_food_complete where date(dfc_date)=date(:date) and dfc_mi_seq =:miSeq", nativeQuery = true)
  public DayFoodCompleteEntity findByMiSeqAndDfcRegDt(@Param("miSeq") Long miSeq, @Param("date") LocalDateTime dfRegDt);
}
