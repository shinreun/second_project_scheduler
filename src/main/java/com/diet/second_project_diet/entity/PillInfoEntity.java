package com.diet.second_project_diet.entity;

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
@Table(name = "pill_info")
@Builder
public class PillInfoEntity {
  @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Schema(description = "약 정보 seq 번호", example = "1")
  @Column(name = "pi_seq")private Long piSeq;
  @Schema(description = "회원 정보 seq 번호", example = "1")
  @ManyToOne @JoinColumn(name = "pi_mi_seq") MemberInfoEntity member;
  // @Column(name = "pi_mi_seq") private Long piMiSeq;
  @Schema(description = "약 이름", example = "고혈압")
  @Column(name = "pi_name") private String piName;
  @Schema(description = "하루 복용 횟수", example = "3")
  @Column(name = "pi_amount") private Integer piAmount;
  @Schema(description = "약 섭취 여부", example = "1")
  @Column(name = "pi_status") private Integer piStatus;
}
