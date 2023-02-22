package com.diet.second_project_diet.water.vo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class mgWeekListVO {
    
    private String wiDate;
    private Integer wiCount;
    private Boolean wiGoal; 
    
}
