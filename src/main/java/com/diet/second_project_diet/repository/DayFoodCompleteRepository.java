package com.diet.second_project_diet.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;

public interface DayFoodCompleteRepository extends JpaRepository<DayFoodCompleteEntity, Long>{
   
    public Integer countByDfcMiSeqAndDfcDate(Long dfcMiSeq, LocalDateTime dfcDate);

    public DayFoodCompleteEntity findByDfcMiSeqAndDfcDate(Long dfcMiseq, LocalDate dfcDate);

    public List<DayFoodCompleteEntity> findByDfcMiSeq(Long dfcMiSeq);
   
    public DayFoodCompleteEntity deleteByDfcMiSeqAndDfcDate(Long dfcMiSeq, LocalDateTime dfcDate);

// @Query(value = "insert into day_food_complete(dfc_mi_seq, dfc_total_cal, dfc_date) select dfc_mi_seq, dfc_total_cal, df_reg_dt from(select df_mi_seq as dfc_mi_seq, sum(df_kcal) as dfc_total_cal, date(df_reg_dt) as dt, df_reg_dt from day_food :data group by df_mi_seq, dt) a", nativeQuery = true)
//   List<DayFoodEntity> insertData(@Param ("member") Long member, @Param("date") LocalDate date );
}
