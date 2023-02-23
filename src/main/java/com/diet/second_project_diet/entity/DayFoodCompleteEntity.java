package com.diet.second_project_diet.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "day_food_complete")
@Builder
public class DayFoodCompleteEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "회원 식단 완료 정보 seq 번호", example = "1")
  @Column(name = "dfc_seq")
  private Long dfcSeq;
  @JsonIgnore
  @Schema(description = "회원 정보")
  @ManyToOne @JoinColumn(name = "dfc_mi_seq") MemberInfoEntity member;
  // @Column (name = "dfc_mi_seq") private Long dfcMiSeq;
  @Schema(description = "해당 일자의 총 섭취 칼로리", example = "2000")
  @Column (name = "dfc_total_cal") private Integer dfcTotalCal;
  @Schema(description = "해당 식단의 목표 성공여부", example = "false")
  @Column (name = "dfc_goal") @ColumnDefault("false") private Boolean dfcGoal;
  @Schema(description = "해당 식단의 일자", example = "2022-02-02")
  @Column (name = "dfc_date")  private LocalDate dfcDate;
}
