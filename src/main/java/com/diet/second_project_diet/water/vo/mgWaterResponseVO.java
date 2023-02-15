package com.diet.second_project_diet.water.vo;

import jakarta.validation.OverridesAttribute.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class mgWaterResponseVO {
    private Boolean status;
    private String message;
   //private List<mgWaterListResponseVO> mgWaterListResponseVOList;

    
}
