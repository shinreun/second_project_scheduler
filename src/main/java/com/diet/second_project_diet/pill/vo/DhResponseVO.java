package com.diet.second_project_diet.pill.vo;

import org.aspectj.apache.bcel.classfile.Code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DhResponseVO {
      @Schema(description = "상태값", example = "true")
      public boolean status;
      @Schema(description = "메시지", example = "메시지입니다")
      public String message;
}
