package com.diet.second_project_diet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diet.second_project_diet.entity.DietCalorieExEntity;

public interface DietCalorieExRepository extends JpaRepository<DietCalorieExEntity, Long>{
  DietCalorieExEntity findByDceSeq(Long dceSeq);

  List<DietCalorieExEntity> findByDceContentContains(String dceContent);

  Integer countByDceContent(String dceContent);
  
  DietCalorieExEntity findByDceContent(String dceContent);
  
}
