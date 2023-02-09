package com.diet.second_project_diet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "day_food")
@Builder
public class DayFoodEntity {
  @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column (name="df_seq") private Long dfSeq; 
  @Column (name="df_mi_seq") private Long dfMiSeq; 
  @Column (name="df_menu") private String dfMenu; 
  @Column (name="df_img") private String dfImg; 
  @Column (name="df_reg_dt") private Date dfRegDt; 
  @Column (name="df_kcal") private Integer dfKcal; 
}
