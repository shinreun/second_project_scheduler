package com.diet.second_project_diet.pill.vo;

import java.util.List;

import com.diet.second_project_diet.entity.PillInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DhPillInfoResponseVO {
    private List<PillInfoEntity> list;
    @Schema(description = "총 회원 수", example = "121")
    private Long total;
    @Schema(description = "총 페이지 수", example = "2")
    private Integer totalPage;
    @Schema(description = "조회한 페이지(1 페이지 -> 0 / 2페이지 -> 1", example = "0")
    private Integer currentPage;

    
}
