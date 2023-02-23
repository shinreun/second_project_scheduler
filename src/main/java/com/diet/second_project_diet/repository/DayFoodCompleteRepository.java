package com.diet.second_project_diet.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;

import org.springframework.data.repository.query.Param;

public interface DayFoodCompleteRepository extends JpaRepository<DayFoodCompleteEntity, Long>{
   
    public Integer countByMemberAndDfcDate(MemberInfoEntity member, LocalDateTime dfcDate);

    public DayFoodCompleteEntity findByMemberAndDfcDate(MemberInfoEntity member, LocalDate dfcDate);

    public List<DayFoodCompleteEntity> findByMemberOrderByDfcDate(MemberInfoEntity member);
   
    public DayFoodCompleteEntity deleteByMemberAndDfcDate(MemberInfoEntity member, LocalDateTime dfcDate);

    @Query(value = "select * from day_food_complete where date(dfc_date)=date(:date) and dfc_mi_seq =:miSeq", nativeQuery = true)
    public DayFoodCompleteEntity findByMiSeqAndDfcRegDt(@Param("miSeq") Long miSeq,
        @Param("date") LocalDateTime dfcRegDt);
    
  @Query(value = "select a from DayFoodCompleteEntity a where year(a.dfcDate)=year(:date) and week(a.dfcDate)=week(:date) and a.member =:member order by a.dfcDate")
  public List<DayFoodCompleteEntity> findWeeklyCal(@Param("member") MemberInfoEntity member, @Param("date") LocalDate dfcRegDt);
}
