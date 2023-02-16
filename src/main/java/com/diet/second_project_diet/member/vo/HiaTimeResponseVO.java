package com.diet.second_project_diet.member.vo;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaTimeResponseVO {
    private Boolean status;
    private String message;
    private Integer dDay;

}
