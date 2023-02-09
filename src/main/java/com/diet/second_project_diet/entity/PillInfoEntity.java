package com.diet.second_project_diet.entity;

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
@Table(name = "pill_info")
@Builder
public class PillInfoEntity {
  @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column(name = "pi_seq") private Long piSeq;
  @Column(name = "pi_mi_seq") private Long piMiSeq;
  @Column(name = "pi_name") private String piName;
  @Column(name = "pi_amount") private Integer piAmount;
  @Column(name = "pi_status") private Integer piStatus;
}
