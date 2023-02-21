package com.diet.second_project_diet.food.vo;

import com.diet.second_project_diet.entity.DietCalorieExEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReDietCalorieResponseVO {
  @Schema(description = "상태값", example = "true")
  private Boolean status;
  @Schema(description = "메시지", example = "메시지입니다")
  private String message;
  @Schema(description = "다이어트 식단 예시 목록")
  private List<DietCalorieExEntity> data;

}
