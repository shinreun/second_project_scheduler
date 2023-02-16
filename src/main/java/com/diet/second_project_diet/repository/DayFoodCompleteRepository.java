package com.diet.second_project_diet.repository;

import java.time.LocalDateTime;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;
import org.springframework.data.repository.query.Param;

public interface DayFoodCompleteRepository extends JpaRepository<DayFoodCompleteEntity, Long>{
   
    public Integer countByDfcMiSeqAndDfcDate(Long dfcMiSeq, LocalDateTime dfcDate);

    public DayFoodCompleteEntity findByDfcMiSeqAndDfcDate(Long dfcMiseq, LocalDate dfcDate);

    public List<DayFoodCompleteEntity> findByDfcMiSeq(Long dfcMiSeq);
   
    public DayFoodCompleteEntity deleteByDfcMiSeqAndDfcDate(Long dfcMiSeq, LocalDateTime dfcDate);

    @Query(value = "select * from day_food_complete where date(dfc_date)=date(:date) and dfc_mi_seq =:miSeq", nativeQuery = true)
  public DayFoodCompleteEntity findByMiSeqAndDfcRegDt(@Param("miSeq") Long miSeq, @Param("date") LocalDateTime dfcRegDt);
}
