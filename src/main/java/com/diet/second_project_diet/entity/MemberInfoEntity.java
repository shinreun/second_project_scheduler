package com.diet.second_project_diet.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
@Table(name = "member_info")
@Builder
public class MemberInfoEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mi_seq") private Long miSeq;
  @Column(name = "mi_name") private String miName;
  @Column(name = "mi_id") private String miId;
  @Column(name = "mi_pwd") private String miPwd;
  @Column(name = "mi_birth") private LocalDate miBirth;
  @Column(name = "mi_gen") private Integer miGen;
  @Column(name = "mi_address") private String miAddress;
  @Column(name = "mi_status") private Integer miStatus;
  @Column(name = "mi_tall") private Integer miTall;
  @Column(name = "mi_weight") private Integer miWeight;
  @Column(name = "mi_hard") private Integer miHard;
  @Column(name = "mi_kcal") private Integer miKcal;
  @Column(name = "mi_time") private LocalDate miTime;
  @Column(name = "mi_water") private Integer miWater;
  @Column(name = "mi_token") private String miToken;
}
