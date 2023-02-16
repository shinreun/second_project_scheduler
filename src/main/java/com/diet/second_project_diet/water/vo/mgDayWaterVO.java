package com.diet.second_project_diet.water.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class mgDayWaterVO {
    private String wiDate;
    private Integer wiCount;
    private Boolean wiGoal;
    private String  wiSuccess;
     private Boolean status;
    private String message;
    
}
