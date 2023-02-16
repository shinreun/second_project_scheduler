package com.diet.second_project_diet.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diet.second_project_diet.entity.DayFoodEntity;

import com.diet.second_project_diet.entity.MemberInfoEntity;

public interface DayFoodRepository extends JpaRepository<DayFoodEntity, Long>{
  public Integer countByMemberAndDfRegDt(MemberInfoEntity member, Date dfRegDt);
  //   public DayFoodEntity findByMemberAndDfRegDt(MemberInfoEntity member, LocalDate dfRegDt);

  public List<DayFoodEntity> findByMemberAndDfRegDt(MemberInfoEntity member, LocalDateTime dfRegDt);

  public List<DayFoodEntity> findByMember(MemberInfoEntity member);
  
  @Query(value = "select * from day_food where date(df_reg_dt)=date(:date) and df_mi_seq =:miSeq", nativeQuery = true)
  public List<DayFoodEntity> findByMiSeqAndDfRegDt(@Param("miSeq") Long miSeq, @Param("date") LocalDateTime dfRegDt);

  public DayFoodEntity findByDfSeq(Long dfSeq);

  public Integer countByDfSeq(Long dfSeq);
}
