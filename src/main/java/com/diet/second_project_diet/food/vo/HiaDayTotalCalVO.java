package com.diet.second_project_diet.food.vo;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HiaDayTotalCalVO {
    private Long miSeq;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime day;
}   
