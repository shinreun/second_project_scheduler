package com.diet.second_project_diet.food.vo;

import com.diet.second_project_diet.entity.DietSuggestEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReDietSuggestWeekResponseVO {
  @Schema(description = "날짜", example = "월/화/수/목/금/토/일")
  private String date;
  @Schema(description = "추천 식단 리스트")
  private List<DietSuggestEntity> data;
}
