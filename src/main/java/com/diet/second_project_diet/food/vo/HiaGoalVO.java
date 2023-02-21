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
public class HiaGoalVO {
    @Schema(description = "총 칼로리", example = "1500")
    private Integer totalCal;
    @Schema(description = "성공 여부", example = "true")
    private Boolean goal;
    @Schema(description = "조회 년.월.일", example = "2023-02-09")
    private LocalDate date;
}
