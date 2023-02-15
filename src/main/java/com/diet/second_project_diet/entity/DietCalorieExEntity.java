package com.diet.second_project_diet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diet_calorie_ex")
@Entity
@Builder
public class DietCalorieExEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "dce_seq") private Long dceSeq;
  @Column(name = "dce_content") private String dceContent;
  @Column(name = "dce_image") private String dceImage;
  @Column(name = "dce_kcal") private Integer dceKcal;
}
