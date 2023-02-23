package com.diet.second_project_diet.pill.vo;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DhMonthlyVO {
   private boolean status;
   private String message;
   private List<DhListResponseVO3> data;
}
