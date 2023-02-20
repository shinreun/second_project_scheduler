package com.diet.second_project_diet.pill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DhRePillInfoInsertVO {
    // @Schema(description = "멤버 seq", example = "1")
    // private Long member;
    
    // @Schema(description = "약 번호", example = "1")
    // private String piSeq;
    @Schema(description = "약 이름", example = "빈혈")
    private String piName;
    @Schema(description = "하루에 먹어야하는 약 갯수", example = "3")
    private Integer piAmount;
}