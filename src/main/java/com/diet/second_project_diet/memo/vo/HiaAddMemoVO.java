package com.diet.second_project_diet.memo.vo;


import com.diet.second_project_diet.entity.MemoInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaAddMemoVO {
    @Schema(description = "오늘의 식단 번호", example = "1")
    private Long dfSeq;
    @Schema(description = "메모 내용")
    private String content;
    

    public HiaAddMemoVO(MemoInfoEntity memo){
        this.dfSeq = memo.getDay().getDfSeq();
        this.content = memo.getMeiContent();
    }
}
