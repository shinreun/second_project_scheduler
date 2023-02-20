package com.diet.second_project_diet.pill.vo;

import java.util.List;

import com.diet.second_project_diet.entity.PillInfoCompleteEntity;
import com.diet.second_project_diet.entity.PillInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DhListResponseVO {
    private boolean status;
    private String message;
    private List<DhListResponseVO2> list;

}
