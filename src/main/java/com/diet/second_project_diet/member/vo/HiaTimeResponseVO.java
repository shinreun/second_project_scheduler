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
public class HiaTimeResponseVO {
    @Schema(description = "상태" , example = "true")
    private Boolean status;
    @Schema(description = "메세지", example = "000 정보가 출력되었습니다.")
    private String message;
    @Schema(description = "D-Day 출력", example = "60")
    private Integer dDay;

}
