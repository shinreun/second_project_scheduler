package com.diet.second_project_diet.weight.vo;

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
public class ReDiffWeightResponseVO {
  @Schema(description = "몸무게 정보 seq 번호", example="1")
  private Long weiSeq;
  @Schema(description = "몸무게", example="60")
  private Double weiWeight;
  @Schema(description = "몸무게 입력 날짜", example="2023-02-15")
  private LocalDate weiDate;
  @Schema(description = "몸무게 차이", example="2")
  private Double difference;
}
