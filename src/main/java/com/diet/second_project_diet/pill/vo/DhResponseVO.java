package com.diet.second_project_diet.pill.vo;

import org.aspectj.apache.bcel.classfile.Code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DhResponseVO {
    public boolean status;
    public String message;
}
