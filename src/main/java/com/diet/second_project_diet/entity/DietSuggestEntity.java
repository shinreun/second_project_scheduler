package com.diet.second_project_diet.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diet_suggest")
@Builder
public class DietSuggestEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "추천 식단 seq 번호", example = "1")
  @Column(name = "diet_seq") private Long dietSeq;
  @Schema(description = "추천 식단 메뉴 이름", example = "닭가슴살 샐러드")
  @Column(name = "diet_content") private String dietContent;
  @Schema(description = "추천 식단 시간대(0.아침/1.아침 간식/2.점심/3.점심간식/4.저녁/5.저녁간식)", example = "0")
  @Column(name = "diet_status") private Integer dietStatus;
  @Schema(description = "추천 식단 추천 식단 칼로리", example = "1200")
  @Column(name = "diet_total_cal") private Integer dietTotalCal;
  @Schema(description = "추천 식단 해당 날짜", example = "2022-03-04")
  @Column(name = "diet_date") private LocalDate dietDate;
  @Schema(description = "추천 식단 해당 다이어트 강도(0.쉬움/1.유지어터/2.다이어터/3.슈퍼다이어터)", example = "1")
  @Column(name = "diet_hard") private Integer dietHard;
}
