package com.diet.second_project_diet.food2.vo;

import java.util.List;

import com.diet.second_project_diet.entity.DayFoodEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReDietGetVO {
  private List<DayFoodEntity> list;
}
