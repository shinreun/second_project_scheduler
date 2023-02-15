package com.diet.second_project_diet.entity;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
  @Column(name = "wei_seq") private Long weiSeq;
  @JsonIgnore @ManyToOne @JoinColumn(name = "wei_mi_seq") MemberInfoEntity member;
  // @Column(name = "wei_mi_seq") private Long weiMiSeq;
  @Column(name = "wei_weight") private Double weiWeight;
  @Column(name = "wei_date") private LocalDate weiDate;
}
