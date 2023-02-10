package com.diet.second_project_diet.food2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReDietInsertVO {
  private Long miSeq;
  private String menu;
  private Integer kcal;
}
