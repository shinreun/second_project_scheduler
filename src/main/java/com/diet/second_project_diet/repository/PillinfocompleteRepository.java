package com.diet.second_project_diet.repository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.PillInfoCompleteEntity;
import com.diet.second_project_diet.entity.PillInfoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PillinfocompleteRepository extends JpaRepository<PillInfoCompleteEntity, Long>{

    public PillInfoCompleteEntity findByPicSeq(Long picSeq);

    public List<PillInfoCompleteEntity> findByPill(PillInfoEntity pill);
    // public List<PillInfoCompleteEntity> findByPill(List<PillInfoEntity> pill);

    public Integer countByPillAndPicGoalAndPicDate(PillInfoEntity pill, Integer goal, LocalDate picDate);
    public Integer countByPillAndPicDate(PillInfoEntity pill, LocalDate picDate);
    
    public List<PillInfoCompleteEntity> findByPillAndPicGoalAndPicDate(PillInfoEntity pill, Integer goal, LocalDate picDate);

    public PillInfoCompleteEntity findByPillAndPicDate(PillInfoEntity pill, LocalDate picDate);

    // @Query(value = "select a from PillInfoCompleteEntity a where year(a.picDate)=year(:date) and month(a.picDate)=month(:date) and a.pill =:pill order by a.picDate ")
    // public List<PillInfoCompleteEntity> findMonthlyCal(@Param("member") MemberInfoEntity member, @Param("date") LocalDate picDate);






}
