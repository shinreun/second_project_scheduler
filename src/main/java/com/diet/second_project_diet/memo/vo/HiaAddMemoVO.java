package com.diet.second_project_diet.memo.vo;


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
    private Long dfSeq;
    private String content;
    

    public HiaAddMemoVO(MemoInfoEntity memo){
        this.dfSeq = memo.getDay().getDfSeq();
        this.content = memo.getMeiContent();
    }
}
