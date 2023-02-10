package com.diet.second_project_diet.food.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;

import lombok.Data;

@Data
public class HiaDayTotalCalVO {
    private Long miSeq;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;
    private Integer goal;
    private Integer totalCal;
    

// public HiaDayTotalCalVO(DayFoodCompleteEntity dfc){
//     this.miSeq = dfc.getMember().getMiSeq();
//     this.day = dfc.getDfcDate().toLocalDate();
//     this.totalCal = dfc.getDfcTotalCal();
// }

    
}   
