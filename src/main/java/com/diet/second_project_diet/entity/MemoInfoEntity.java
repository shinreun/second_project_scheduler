package com.diet.second_project_diet.entity;
import java.time.LocalDate;

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
@Table(name = "memo_info")
@Builder
public class MemoInfoEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "메모 정보 seq 번호", example = "1")
  @Column(name = "mei_seq") private Long meiSeq;
  @Schema(description = "오늘의 식단 seq 번호", example = "1")
  @ManyToOne @JoinColumn(name = "mei_df_seq") DayFoodEntity day; 
  // @Column(name = "mei_mi_seq") private Long meiMiSeq;
  @Schema(description = "메모 내용", example = "오늘 밥이 맛있었다.")
  @Column(name = "mei_content") private String meiContent;
}
