package com.diet.second_project_diet.food.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.diet.second_project_diet.entity.DayFoodEntity;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HiaDayTotalCalVO {
    private Long miSeq;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;
    

public HiaDayTotalCalVO(DayFoodEntity dfc){
    this.miSeq = dfc.getMember().getMiSeq();
    this.day = dfc.getDfRegDt();
}

    
}   
