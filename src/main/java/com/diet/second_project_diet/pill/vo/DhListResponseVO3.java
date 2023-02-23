package com.diet.second_project_diet.pill.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DhListResponseVO3 {
   private Long miSeq;
   private LocalDate date;
   private Boolean success;
}
