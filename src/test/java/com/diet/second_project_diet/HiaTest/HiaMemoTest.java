package com.diet.second_project_diet.HiaTest;


import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.MemoInfoEntity;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.MemoInfoRepository;

@SpringBootTest
public class HiaMemoTest {
    @Autowired MemoInfoRepository meRepo;
    @Autowired MemberInfoRepository mRepo;
    @Autowired DayFoodRepository dfRepo;

    @BeforeEach
    @Test
    public void addMemo(){
        MemoInfoEntity memo = new MemoInfoEntity();
        memo.setMeiSeq(1L);
        memo.setMeiContent("내용");
        Assertions.assertEquals(memo.getMeiSeq(), 1L);
        Assertions.assertEquals(memo.getMeiContent(), "내용");
    }

    @Test
    public void getMemo(){
        DayFoodEntity day = dfRepo.findByDfSeq(1L);
        MemoInfoEntity memo = meRepo.findByDay(day);
        System.out.println(memo);
    }
}
