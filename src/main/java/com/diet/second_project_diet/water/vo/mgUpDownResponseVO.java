package com.diet.second_project_diet.water.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 수정 증가에 쓰이는 responseVO
public class mgUpDownResponseVO {
    private Boolean status;
    private String message;
}
