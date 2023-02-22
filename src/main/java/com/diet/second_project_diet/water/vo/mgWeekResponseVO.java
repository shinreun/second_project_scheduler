package com.diet.second_project_diet.water.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class mgWeekResponseVO {
    private List<mgWeekListVO> list;
    private Boolean status;
    private String message;
}
