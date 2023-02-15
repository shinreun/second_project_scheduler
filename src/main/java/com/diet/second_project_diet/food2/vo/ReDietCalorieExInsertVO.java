package com.diet.second_project_diet.food2.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReDietCalorieExInsertVO {
  @Schema(description = "칼로리 참조표 메뉴 이름", example="치킨")
  private String dceContent;
  // @Schema(description = "칼로리 참조표 이미지 이름", example="chicken.png")
  // private String dceImage;
  @Schema(description = "칼로리 참조표 칼로리", example="1200")
  private Integer dceKcal;
}
