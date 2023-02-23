package com.diet.second_project_diet.entity;

import jakarta.persistence.Column;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "예시 식단 정보 seq 번호", example = "1")
  @Column(name = "dce_seq") private Long dceSeq;
  @Schema(description = "예시 식단 메뉴 이름", example = "치킨")
  @Column(name = "dce_content") private String dceContent;
  @Schema(description = "예시 식단 이미지 uri", example = "image.jpg")
  @Column(name = "dce_image") private String dceImage;
  @Schema(description = "예시 식단 칼로리", example = "300")
  @Column(name = "dce_kcal") private Integer dceKcal;
  @Schema(description = "예시 식단 칼로리 기준", example = "1인분")
  @Column(name = "dce_standard") private String dceStandard;
}
