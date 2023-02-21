package com.diet.second_project_diet.memo.vo;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaMemoDataResponseVO {
    @Schema(description = "상태", example = "true")
    private Boolean status;
    @Schema(description = "메세지", example = "000 정보를 출력합니다.")
    private String message;
    // private MemoInfoEntity data;
    @Schema(description = "메모 정보 vo")
    private HiaGetMemoVO vo;
}
