package com.diet.second_project_diet.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
  @Column(name = "diet_seq") private Long dietSeq;
  @Column(name = "diet_content") private String dietContent;
  @Column(name = "diet_status") private Integer dietStatus;
  @Column(name = "diet_total_cal") private Integer dietTotalCal;
  @Column(name = "diet_date") private Date dietDate;
  @Column(name = "diet_hard") private Integer dietHard;
}
