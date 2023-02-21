package com.diet.second_project_diet.memo.vo;

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
public class HiaGetMemoVO {
    @Schema(description = "메모 내용")
    String content;
    // LocalDate day;
}
