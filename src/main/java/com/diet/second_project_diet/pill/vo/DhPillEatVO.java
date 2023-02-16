package com.diet.second_project_diet.pill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DhPillEatVO {
    private Long member;
    private String piName;
    private Integer piAmount;
    private Integer piStatus;
    
}
