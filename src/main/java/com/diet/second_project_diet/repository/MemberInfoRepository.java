package com.diet.second_project_diet.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.MemberInfoEntity;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long> {
  MemberInfoEntity findByMiSeq(Long miSeq);
  
  public MemberInfoEntity findByMiTokenIs(String token);
  
  public MemberInfoEntity findByMiIdAndMiPwd(String id, String pwd);

  public Integer countByMiId(String miId);
}
