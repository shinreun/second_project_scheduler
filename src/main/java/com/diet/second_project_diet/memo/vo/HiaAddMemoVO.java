package com.diet.second_project_diet.memo.vo;

import java.time.LocalDate;

import com.diet.second_project_diet.entity.MemoInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaAddMemoVO {
    private Long miSeq;
    private String content;
    private LocalDate date;

    public HiaAddMemoVO(MemoInfoEntity memo){
        this.miSeq = memo.getMember().getMiSeq();
        this.content = memo.getMeiContent();
        this.date = memo.getMeiDate();
    }
}
