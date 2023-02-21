package com.diet.second_project_diet.food.vo;

import java.util.List;

import com.diet.second_project_diet.entity.DietSuggestEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReDietSuggestResponseVO {
  @Schema(description = "오늘의 식단 추천 데이터")
  List<DietSuggestEntity> data;
  @Schema(description = "상태값", example = "true")
  private Boolean status;
  @Schema(description = "메시지", example = "메시지입니다")
  private String message;

}
