package com.diet.second_project_diet.repository;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.WaterInfoEntity;
import org.springframework.data.domain.Pageable;

public interface WaterInfoRepository extends JpaRepository<WaterInfoEntity, Long> {


  // // 쿼리문을 만들어서 제대로된 물 조회를 만들려고 했음 - 총음수량
  // @Query(value = "select sum(wi_count) from water_info wi;" , nativeQuery = true)
  // public List<WaterInfoEntity> countByWiCount(@Param("wi_count")Long wiCount);
  // //   // 어디 엔터티에서 값을 가져오는지 출처를 밝혀야한다
    
  public  WaterInfoEntity findByWiSeq(Long wiSeq);  // 증가에 사용
  //public Integer countBy  // 증가 中 생성

  // // 월~일 조회를 위한 것 
  
  
 public Page<WaterInfoEntity> findByWiCountContains(String wiCount, Pageable pageable); // w조회를 위한 것  
 public Integer countByWiCount (Integer wiCount); // 그냥단순히숫자세아림
 public Integer countByMemberAndWiDate(MemberInfoEntity member, LocalDate wiDate); // 직관적으로 뵜을 때 정보를 찾아낼 수 있는 것
 public WaterInfoEntity findByMemberAndWiDate(MemberInfoEntity member, LocalDate wiDate); // 이것을 숫자를 셍아리셈.. ! 

  
}
