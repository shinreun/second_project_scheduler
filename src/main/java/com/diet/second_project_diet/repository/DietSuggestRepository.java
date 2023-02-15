package com.diet.second_project_diet.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diet.second_project_diet.entity.DietSuggestEntity;
import java.util.List;
import java.time.LocalDate;

public interface DietSuggestRepository extends JpaRepository<DietSuggestEntity, Long>{
  List< DietSuggestEntity> findByDietHardAndDietDate(Integer dietHard, LocalDate date);
}
