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
public class HiaUpdateMemberInfoVO {
    @Schema(description = "회원번호", example = "1")
    private Long seq;
    @Schema(description = "비밀번호", example = "123456")
    private String pwd;
    @Schema(description = "주소", example = "대구광역시 중구")
    private String address;
    @Schema(description = "키", example = "180")
    private Integer tall;
    @Schema(description = "몸무게", example = "80")
    private Integer weight;
    @Schema(description = "다이어트 강도", example = "1")
    private Integer hard;
    @Schema(description = "목표 칼로리", example = "1500")
    private Integer cal;
    @Schema(description = "목표 몸무게", example = "85")
    private Double kg;
    @Schema(description = "목표 음수량", example = "8")
    private Integer water;
    @Schema(description = "목표 기간", example = "100")
    private Integer time;
    @Schema(description = "회원 상태", example = "1")
    private Integer status;
}
