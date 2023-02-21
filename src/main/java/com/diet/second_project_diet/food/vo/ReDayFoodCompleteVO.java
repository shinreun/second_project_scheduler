package com.diet.second_project_diet.food.vo;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReDayFoodCompleteVO {
  @Schema(description = "칼로리 값")
  private List<ReDayFoodCompleteResponseVO> data;
  @Schema(description = "상태값", example = "true")
  private Boolean status;
  @Schema(description = "메시지", example = "메시지입니다")
  private String message;
  
}
