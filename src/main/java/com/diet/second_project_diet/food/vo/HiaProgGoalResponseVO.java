package com.diet.second_project_diet.food.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaProgGoalResponseVO {
    private Boolean status;
    private String message;
    private String data;
}
