package com.diet.second_project_diet.food.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReDietInsertVO {
  @Schema(description = "해당 메뉴 이름", example = "떡볶이")
  private String menu;
  @Schema(description = "칼로리", example = "480")
  private Integer kcal;
  @Schema(description = "메모 내용")
  private String content;
}
