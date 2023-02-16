package com.diet.second_project_diet.memo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaMemoResponseVO {
    private Boolean status;
    private String message;
}
