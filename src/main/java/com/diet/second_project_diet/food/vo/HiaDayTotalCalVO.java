package com.diet.second_project_diet.food.vo;

import java.time.LocalDateTime;

import com.diet.second_project_diet.entity.DayFoodEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HiaDayTotalCalVO {
    @Schema(description = "회원번호", example = "1")
    private Long miSeq;
    @Schema(description = "해당일", example = "2023-02-09T00:00:00")
    private LocalDateTime day;
    
    

public HiaDayTotalCalVO(DayFoodEntity df){
    this.miSeq = df.getMember().getMiSeq();
    this.day = df.getDfRegDt();
   
}

    
}   
