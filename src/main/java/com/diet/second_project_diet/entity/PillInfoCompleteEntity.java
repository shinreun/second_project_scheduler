package com.diet.second_project_diet.entity;
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
@Table(name = "pill_info_complete")
@Builder
public class PillInfoCompleteEntity {
  @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column(name = "pic_seq") private Long picSeq;
  @ManyToOne @JoinColumn(name = "pic_mi_seq") MemberInfoEntity member;
  // @Column(name = "pic_mi_seq") private Long picMiSeq;
  @Column(name = "pic_total") private Integer picTotal;
  @Column(name = "pic_goal") private Integer picGoal;
  @Column(name = "pic_date") private Date picDate;
}
