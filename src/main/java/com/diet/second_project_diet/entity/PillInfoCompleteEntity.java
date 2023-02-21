package com.diet.second_project_diet.entity;
import java.time.LocalDate;
import java.util.Date;

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
@Table(name = "pill_info_complete")
@Builder
public class PillInfoCompleteEntity {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Schema(description = "약 섭취여부 정보 seq 번호", example = "1")
    @Column(name = "pic_seq") private Long picSeq;
    @Schema(description = "약 정보 seq 번호", example = "1")
    @ManyToOne @JoinColumn(name = "pic_pi_seq") PillInfoEntity pill;
    // @Column(name = "pic_mi_seq") private Long picMiSeq;
    @Schema(description = "총 복용 횟수", example = "3")
    @Column(name = "pic_total") private Integer picTotal;
    @Schema(description = "목표 달성 여부", example = "1")
    @Column(name = "pic_goal") private Integer picGoal;
    @Schema(description = "해당 날짜", example = "2023-02-30")
    @Column(name = "pic_date") private LocalDate picDate;
}
