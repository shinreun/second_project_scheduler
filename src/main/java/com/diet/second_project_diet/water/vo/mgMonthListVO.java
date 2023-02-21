package com.diet.second_project_diet.water.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.diet.second_project_diet.entity.WaterInfoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class mgMonthListVO {
    private List<WaterInfoEntity> list;
    private String success;
    private Boolean status;
    private String message;

    
}
