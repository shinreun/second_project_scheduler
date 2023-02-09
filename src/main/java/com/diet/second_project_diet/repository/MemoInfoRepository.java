package com.diet.second_project_diet.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.MemoInfoEntity;

public interface MemoInfoRepository extends JpaRepository<MemoInfoEntity, Long>{
  
}
