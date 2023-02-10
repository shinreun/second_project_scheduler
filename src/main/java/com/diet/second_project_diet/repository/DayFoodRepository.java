package com.diet.second_project_diet.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;

public interface DayFoodRepository extends JpaRepository<DayFoodEntity, Long>{
  DayFoodEntity findByDfImg(String dfImg);

  List<DayFoodEntity> findByMemberAndDfRegDt(MemberInfoEntity data, LocalDate date);
}
