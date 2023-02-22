package com.diet.second_project_diet.entity;
import java.time.LocalDate;
import java.util.Date;

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
@Table(name = "water_info")
@Builder
public class WaterInfoEntity {
  @Id @GeneratedValue (strategy = GenerationType.IDENTITY) 
  @Schema(description = "물 정보 seq 번호", example = "1")
  @Column(name = "wi_seq") private Long wiSeq;
  @JsonIgnore
  @Schema(description = "회원 정보 seq 번호", example = "1")
  @ManyToOne @JoinColumn(name = "wi_mi_seq") MemberInfoEntity member;
  // @Column(name = "wi_mi_seq") private Long wiMiSeq;
  @Schema(description = "목표 음수량", example = "1")
  @Column(name = "wi_count") private Integer wiCount; // 양
  @Schema(description = "해당 날짜", example = "2023-02-03")
  @Column(name = "wi_date") private LocalDate wiDate; // 날짜
  @Schema(description = "목표 성공 여부", example = "true")
  @Column(name = "wi_goal") private Boolean wiGoal; // 목표량
}
