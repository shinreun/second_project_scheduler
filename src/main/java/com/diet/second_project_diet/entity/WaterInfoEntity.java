package com.diet.second_project_diet.entity;
import java.time.LocalDate;
import java.util.Date;
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
  @Column(name = "wi_seq") private Long wiSeq;
  @ManyToOne @JoinColumn(name = "wi_mi_seq") MemberInfoEntity member;
  // @Column(name = "wi_mi_seq") private Long wiMiSeq;
  @Column(name = "wi_count") private Integer wiCount; // 양
  @Column(name = "wi_date") private LocalDate wiDate; // 날짜
  @Column(name = "wi_goal") private Boolean wiGoal; // 목표량
}
