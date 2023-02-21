package com.diet.second_project_diet.entity;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weight_info")
@Builder
public class WeightInfoEntity {
  @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Schema(description = "몸무게 정보 seq 번호", example = "1")
  @Column(name = "wei_seq") private Long weiSeq;
  @Schema(description = "회원 정보 seq 번호", example = "1")
  @JsonIgnore @ManyToOne @JoinColumn(name = "wei_mi_seq") MemberInfoEntity member;
  // @Column(name = "wei_mi_seq") private Long weiMiSeq;
  @Schema(description = "몸무게 수치", example = "86.5")
  @Column(name = "wei_weight") private Double weiWeight;
  @Schema(description = "해당 날짜", example = "2023-02-03")
  @Column(name = "wei_date") private LocalDate weiDate;
}
