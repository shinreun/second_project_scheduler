package com.diet.second_project_diet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.PillInfoCompleteEntity;
import com.diet.second_project_diet.entity.PillInfoEntity;

import java.util.List;

public interface PillInfoRepository extends JpaRepository<PillInfoEntity, Long>{
  
    public PillInfoEntity findByPiSeq(Long piSeq);
    
    public PillInfoEntity findByPiName(String piName);
    
    public List<PillInfoEntity> findByMember(MemberInfoEntity member);
    public List<PillInfoEntity> findByMemberAndPiStatus(MemberInfoEntity member, Integer piStatus);

    public Integer countByMemberAndPiName(MemberInfoEntity member, String piName);

    public PillInfoEntity findByPiSeqAndPiStatus(Long piSeq, Integer status);

    // public PillInfoCompleteEntity findByPicSeq(Long picSeq);

    // 출력 - sample 1차 프로젝트꺼 가져운거
    // postman으로 조회가 되긴함
    // @Query(value = "select a from PillInfoEntity a") 
    // Page<PillInfoEntity> getPillInfoList(Pageable Pageable);
    
}
