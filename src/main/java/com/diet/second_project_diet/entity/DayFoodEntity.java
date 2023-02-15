package com.diet.second_project_diet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "day_food")
@Builder
public class DayFoodEntity {
  @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column(name = "df_seq")
  private Long dfSeq;
  @JsonIgnore @ManyToOne @JoinColumn(name = "df_mi_seq") MemberInfoEntity member; 
  // @Column (name="df_mi_seq") private Long dfMiSeq; 
  @Column (name="df_menu") private String dfMenu; 
  @Column (name="df_img") private String dfImg; 
  @Column (name="df_reg_dt") private LocalDateTime dfRegDt; 
  @Column (name="df_kcal") private Integer dfKcal; 
}
