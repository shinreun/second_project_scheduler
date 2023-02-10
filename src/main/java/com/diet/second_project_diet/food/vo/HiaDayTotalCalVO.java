package com.diet.second_project_diet.food.vo;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;

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
    private LocalDateTime day;
    private Integer goal;
    private Integer totalCal;
    

// public HiaDayTotalCalVO(DayFoodCompleteEntity dfc){
//     this.miSeq = dfc.getMember().getMiSeq();
//     this.day = dfc.getDfcDate().toLocalDate();
//     this.totalCal = dfc.getDfcTotalCal();
// }

    
}   
