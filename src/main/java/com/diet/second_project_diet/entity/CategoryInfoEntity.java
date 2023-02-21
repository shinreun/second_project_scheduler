package com.diet.second_project_diet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category_info")
@Entity
@Builder
public class CategoryInfoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "사이드바 카테고리 정보 seq 번호", example = "1")
  @Column(name = "cate_seq") private Long cateSeq;
  @Schema(description = "사이드바 카테고리 이름", example = "물")
  @Column(name = "cate_name") private String cateName;
}
