package com.diet.second_project_diet.food.vo;

import com.diet.second_project_diet.entity.DayFoodEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaDetailResponseVO {
    private Boolean status;
    private String message;
    private DayFoodEntity data;
    private String memo;
}
