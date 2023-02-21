package com.diet.second_project_diet.food.vo;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReDietSuggestInsertVO {
  @Schema(description = "추천 식단 내용", example="현미밥 100g, 닭가슴살 100g")
  private String dietContent;
  @Schema(description = "추천 식단 시간 (0.아침/1.점심/2.저녁/3.야식/4.아침간식/5.점심간식/6.저녁간식)", example="0")
  private Integer dietStatus;
  @Schema(description = "추천 식단 칼로리", example="500")
  private Integer dietTotalCal;
  @Schema(description = "추천 식단 일자", example="2023-02-23")
  private LocalDate dietDate;
  @Schema(description = "추천 식단의 해당 다이어트 강도(0:건강/1:유지어터/2:다이어터/3:하드다이어터)", example="1")
  private Integer dietHard;
}
