package com.diet.second_project_diet.entity;
import java.util.Date;

import jakarta.persistence.Column;
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
@Table(name = "weight_info")
@Builder
public class WeightInfoEntity {
  @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column(name = "wei_seq") private Long weiSeq;
  @Column(name = "wei_mi_seq") private Long weiMiSeq;
  @Column(name = "wei_weight") private Integer weiWeight;
  @Column(name = "wei_date") private Date weiDate;
}
