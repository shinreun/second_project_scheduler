package com.diet.second_project_diet.food.vo;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReDayFoodCompleteResponseVO {
  @Schema(description = "칼로리 섭취량 seq 번호", example="2")
  private Long dfcSeq;
  @Schema(description = "총 섭취한 칼로리", example="260")
  private Integer dfcTotalCal;
  @Schema(description = "요일", example="Monday")
  private String date;
  @Schema(description = "일자", example="2023-02-23")
  private LocalDate dfcDate;
}
