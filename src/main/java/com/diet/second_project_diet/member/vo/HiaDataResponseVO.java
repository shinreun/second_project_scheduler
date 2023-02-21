package com.diet.second_project_diet.member.vo;

import com.diet.second_project_diet.entity.MemberInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaDataResponseVO {
    @Schema(description = "상태" , example = "true")
    private Boolean status;
    @Schema(description = "메세지", example = "000 정보가 출력되었습니다.")
    private String message;
    @Schema(description = "출력 데이터")
    private MemberInfoEntity data;
}
