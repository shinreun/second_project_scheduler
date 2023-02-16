package com.diet.second_project_diet.food.vo;

import java.util.List;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaGoalResponseVO {
    private Boolean status;
    private String message;
    private List<DayFoodCompleteEntity> list;
}
