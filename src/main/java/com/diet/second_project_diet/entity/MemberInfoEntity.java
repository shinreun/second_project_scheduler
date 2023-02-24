package com.diet.second_project_diet.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "멤버 seq 번호", example = "1")
  @Column(name = "mi_seq") private Long miSeq;
  @Schema(description = "멤버 이름", example = "사용자")
  @Column(name = "mi_name") private String miName;
  @Schema(description = "멤버 아이디", example = "user1")
  @Column(name = "mi_id") private String miId;
  @JsonIgnore
  @Schema(description = "멤버 비밀번호", example = "1234")
  @Column(name = "mi_pwd") private String miPwd;
  @Schema(description = "멤버 나이", example = "24")
  @Column(name = "mi_age") private Integer miAge;
  @Schema(description = "멤버 성별(0.남자/1.여자)", example = "0")
  @Column(name = "mi_gen") private Integer miGen;
  @Schema(description = "멤버 주소", example = "천국")
  @Column(name = "mi_address") private String miAddress;
  @Schema(description = "멤버 상태 (0.정상사용/1.정지/2.탈퇴)", example = "0")
  @Column(name = "mi_status") private Integer miStatus;
  @Schema(description = "멤버 키", example = "162")
  @Column(name = "mi_tall") private Integer miTall;
  @Schema(description = "멤버 현재 몸무게", example = "60")
  @Column(name = "mi_weight") private Integer miWeight;
  @Schema(description = "멤버 다이어트 강도(0.쉬움/1.유지어터/2.다이어터/3.슈퍼다이어터)", example = "1")
  @Column(name = "mi_hard") private Integer miHard;
  @Schema(description = "멤버의 하루 섭취 목표 칼로리", example = "1200")
  @Column(name = "mi_kcal") private Integer miKcal;
  @Schema(description = "멤버의 다이어트 시작 일자", example = "2022-12-20")
  @Column(name = "mi_start_time") private LocalDate miStartTime;
  @Schema(description = "멤버 다이어트 종료 일자", example = "2023-02-28")
  @Column(name = "mi_end_time") private LocalDate miEndTime;
  @Schema(description = "멤버 목표 음수량(200ml잔 단위)", example = "8")
  @Column(name = "mi_water") private Integer miWater;
  @Schema(description = "멤버 토큰 번호", example = "qwert12")
  @Column(name = "mi_token") private String miToken;
  @Schema(description = "멤버 이미지", example = "image.jpg")
  @Column(name = "mi_img") private String miImg;
  @Schema(description = "멤버 목표 몸무게", example = "50")
  @Column(name = "mi_goal_kg") private Double miGoalKg;
}
