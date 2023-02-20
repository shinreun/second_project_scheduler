package com.diet.second_project_diet.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  @Column(name = "dfc_seq")
  private Long dfcSeq;
  @ManyToOne @JoinColumn(name = "dfc_mi_seq") MemberInfoEntity member;
  // @Column (name = "dfc_mi_seq") private Long dfcMiSeq;
  @Column (name = "dfc_total_cal") private Integer dfcTotalCal;
  @Column (name = "dfc_goal") @ColumnDefault("false") private Boolean dfcGoal;
  // @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column (name = "dfc_date")  private LocalDate dfcDate;
}
