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
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "day_food")
@Builder
public class DayFoodEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "회원 식단 정보 seq 번호", example = "1")
  @Column(name = "df_seq")
  private Long dfSeq;
  @Schema(description = "회원 정보")
  @JsonIgnore @ManyToOne @JoinColumn(name = "df_mi_seq") MemberInfoEntity member; 
  // @Column (name="df_mi_seq") private Long dfMiSeq; 
  @Schema(description = "섭취한 메뉴 이름" , example="닭가슴살")
  @Column (name="df_menu") private String dfMenu; 
  @Schema(description = "섭취한 메뉴 이미지 uri" , example="image.jpg")
  @Column (name="df_img") private String dfImg; 
  @Schema(description = "섭취한 일자 (시간 포함)" , example="2022-02-03T00:00:00")
  @Column (name="df_reg_dt") private LocalDateTime dfRegDt; 
  @Schema(description = "해당 식단의 칼로리" , example="200")
  @Column (name="df_kcal") private Integer dfKcal; 
}
