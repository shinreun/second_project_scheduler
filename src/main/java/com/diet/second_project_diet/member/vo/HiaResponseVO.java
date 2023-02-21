package com.diet.second_project_diet.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaResponseVO {
    @Schema(description = "상태" , example = "true")
    private Boolean status;
    @Schema(description = "메세지", example = "정보가 입력되었습니다.")
    private String message;
}
