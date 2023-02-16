package com.diet.second_project_diet.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.PillInfoEntity;

public interface PillInfoRepository extends JpaRepository<PillInfoEntity, Long>{
  
    public PillInfoEntity findByPiSeq(Long piSeq);

    public PillInfoEntity findByPiName(String piName);
    
}
