package com.diet.second_project_diet.food.vo;

import java.util.List;

import com.diet.second_project_diet.entity.DayFoodEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReGetDailyDietResponseVO {
  @Schema(description = "오늘 하루 식단")
  private List<DayFoodEntity> list;
  @Schema(description = "메모 내용")
  private String memo;
  @Schema(description = "상태값", example = "true")
  private Boolean status;
  @Schema(description = "메시지", example = "메시지입니다")
  private String message;
}
