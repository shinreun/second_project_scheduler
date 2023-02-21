package com.diet.second_project_diet.weight.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReWeightCompareVO {
  @Schema(description = "목표 체중")
  private Double goalWeight;
   @Schema(description = "현재 체중")
  private Double nowWeight;
  @Schema(description = "체중 변화량")
  private Double diff;
  @Schema(description = "상태값", example = "true")
  private Boolean status;
  @Schema(description = "메시지", example = "메시지입니다")
  private String message;
}