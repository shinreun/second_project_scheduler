package com.diet.second_project_diet.repository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.PillInfoCompleteEntity;
import com.diet.second_project_diet.entity.PillInfoEntity;

public interface PillinfocompleteRepository extends JpaRepository<PillInfoCompleteEntity, Long>{

    public PillInfoCompleteEntity findByPicSeq(Long picSeq);

    public List<PillInfoCompleteEntity> findByPill(PillInfoEntity pill);

    public Integer countByPillAndPicGoalAndPicDate(PillInfoEntity pill, Integer goal, LocalDate picDate);

}
