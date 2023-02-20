package com.diet.second_project_diet.repository;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.MemoInfoEntity;

public interface MemoInfoRepository extends JpaRepository<MemoInfoEntity, Long>{
//   public MemoInfoEntity findByMemberAndMeiDate(MemberInfoEntity member, LocalDate meiDate);
public MemoInfoEntity findByDay(DayFoodEntity day);
}
