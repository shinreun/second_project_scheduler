package com.diet.second_project_diet.repository;
import java.lang.reflect.Member;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.WeightInfoEntity;

public interface WeightInfoRepository extends JpaRepository<WeightInfoEntity, Long>{
  WeightInfoEntity findByMemberAndWeiDate(MemberInfoEntity member, LocalDate weiDate);

  WeightInfoEntity findByMemberAndWeiSeq(MemberInfoEntity member, Long weiSeq);
  
  List<WeightInfoEntity> findByMemberOrderByWeiDate(MemberInfoEntity member);

  @Query(value = "select a from WeightInfoEntity a where week(a.weiDate) = week(:date) order by a.weiDate")
  List<WeightInfoEntity> weightListByWeek(@Param("date") LocalDate date);

  @Query(value = "select a from WeightInfoEntity a where month(a.weiDate) = month(:date) and year(a.weiDate) = year(:date)order by a.weiDate")
  List<WeightInfoEntity> weightListByMonth (@Param("date") LocalDate date);

}
