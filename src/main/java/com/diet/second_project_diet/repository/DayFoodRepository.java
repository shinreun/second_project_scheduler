package com.diet.second_project_diet.repository;

import java.time.LocalDate;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.DayFoodEntity;

import com.diet.second_project_diet.entity.MemberInfoEntity;

public interface DayFoodRepository extends JpaRepository<DayFoodEntity, Long>{
  public Integer countByMemberAndDfRegDt(MemberInfoEntity member, LocalDate dfRegDt);
//   public DayFoodEntity findByMemberAndDfRegDt(MemberInfoEntity member, LocalDate dfRegDt);

  public List<DayFoodEntity> findByMemberAndDfRegDt (MemberInfoEntity member, LocalDate dfRegDt);
}
