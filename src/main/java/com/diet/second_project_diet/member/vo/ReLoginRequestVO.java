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
public class ReLoginRequestVO {
    @Schema(description = "아이디", example = "user01")
    private String id;
    @Schema(description = "비밀번호", example = "123456")
    private String pwd;
}
