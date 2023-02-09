package com.diet.second_project_diet.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "day_food_complete")
@Builder
public class DayFoodCompleteEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "dfc_seq") private Long dfcSeq;
  @Column (name = "dfc_mi_seq") private Long dfcMiSeq;
  @Column (name = "dfc_total_cal") private Integer dfcTotalCal;
  @Column (name = "dfc_goal") private Integer dfcGoal;
}
