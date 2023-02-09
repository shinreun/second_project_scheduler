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
@Table(name = "memo_info")
@Builder
public class MemoInfoEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mei_seq") private Long meiSeq;
  @ManyToOne @JoinColumn(name = "mei_mi_seq") MemberInfoEntity member; 
  // @Column(name = "mei_mi_seq") private Long meiMiSeq;
  @Column(name = "mei_content") private String meiContent;
  @Column(name = "mei_date") private Date meiDate;
}
