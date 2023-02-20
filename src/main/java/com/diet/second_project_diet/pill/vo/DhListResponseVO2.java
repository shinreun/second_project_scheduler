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
public class DhListResponseVO2 {
    private Long miSeq;

    private Long pillSeq;
    private String pillName;
    private Integer pillAmount;

}
