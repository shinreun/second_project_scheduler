package com.diet.second_project_diet.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.DietSuggestEntity;
import java.util.List;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DietSuggestRepository extends JpaRepository<DietSuggestEntity, Long>{
  List<DietSuggestEntity> findByDietHardAndDietDate(Integer dietHard, LocalDate date);
  

  @Query(value = "select a from DietSuggestEntity a where week(a.dietDate)=week(:date) and year(a.dietDate)=year(:date) and a.dietHard =:hard order by a.dietDate, a.dietStatus")
  public List<DietSuggestEntity> findWeeklySuggest(@Param("hard") Integer dietHard, @Param("date") LocalDate date);
  
  DietSuggestEntity findByDietSeq(Long dietSeq);
}
