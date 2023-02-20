package com.diet.second_project_diet.food2.vo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReDietSuggestWeeklyFinalVO {
  @Schema(description = "요일별 식단 추천", example = "true")
  private List<ReDietSuggestWeekResponseVO> data;
  @Schema(description = "상태값", example = "true")
  private Boolean status;
  @Schema(description = "메시지", example = "메시지입니다")
  private String message;
}
