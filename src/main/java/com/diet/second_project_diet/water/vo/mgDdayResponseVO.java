package com.diet.second_project_diet.water.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class mgDdayResponseVO {
    private String sucess;
    private String message;
    private Boolean Status;
    
}
