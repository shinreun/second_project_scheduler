package com.diet.second_project_diet.repository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.PillInfoCompleteEntity;
import com.diet.second_project_diet.entity.PillInfoEntity;

public interface PillinfocompleteRepository extends JpaRepository<PillInfoCompleteEntity, Long>{

    public PillInfoCompleteEntity findByPicSeq(Long picSeq);

    public List<PillInfoCompleteEntity> findByPill(PillInfoEntity pill);

    public Integer countByPillAndPicGoalAndPicDate(PillInfoEntity pill, Integer goal, LocalDate picDate);

    // pill 섭취량 수정 기능 만드는중
    // public List<PillInfoEntity> findByMemberAndPiStatus(MemberInfoEntity member, Integer piStatus);
    // public PillInfoCompleteEntity findByPillAndPicDate(PillInfoEntity pill, LocalDate picDate);
    public PillInfoCompleteEntity findByPillAndPicDate(PillInfoEntity pill, LocalDate picDate);
}
