package com.diet.second_project_diet.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.WaterInfoEntity;
import com.diet.second_project_diet.water.vo.mgWeekListVO;

import org.springframework.data.domain.Pageable;

public interface WaterInfoRepository extends JpaRepository<WaterInfoEntity, Long> {

 // public  WaterInfoEntity findByWiSeq(Long wiSeq);  // 증가에 사용

 public Integer countByMemberAndWiDate(MemberInfoEntity member, LocalDate wiDate); // 직관적으로 뵜을 때 정보를 찾아낼 수 있는 것
 public WaterInfoEntity findByMemberAndWiDate(MemberInfoEntity member, LocalDate wiDate); // 이것을 숫자를 셍아리셈.. ! 

 @Query(value="select a from WaterInfoEntity a where week(a.wiDate) = week(:date) and year(a.wiDate) = year(:date)")
 public List<WaterInfoEntity> findByWeek(@Param("date") LocalDate wiDate); //주마다 조회


 

 public WaterInfoEntity findByWiDateAndMember(LocalDate wiDate, MemberInfoEntity member);

//  public WaterInfoEntity findByWiGoal(Boolean wiGoal);

 public List<WaterInfoEntity> findByMember(MemberInfoEntity member);


 
  
}
