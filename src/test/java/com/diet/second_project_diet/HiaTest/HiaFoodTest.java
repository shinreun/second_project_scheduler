package com.diet.second_project_diet.HiaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;
import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.repository.DayFoodCompleteRepository;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;

@SpringBootTest
public class HiaFoodTest {
    @Autowired DayFoodCompleteRepository dfcRepo;
    @Autowired DayFoodRepository dfRepo;
    @Autowired MemberInfoRepository mRepo;

    @BeforeEach
    @Test
    public void sumTotalCal(){
        MemberInfoEntity member = mRepo.findByMiSeq(1L);
        DayFoodCompleteEntity entity = dfcRepo.findByMemberAndDfcDate(member, LocalDate.of(2023,02,10));
        List<DayFoodEntity> list = dfRepo.findByMember(member);
        LocalDate now = entity.getDfcDate();
        Integer totalCal = 0;
            for (int  i =0; i< list.size(); i++) {
                LocalDate now2 = list.get(i).getDfRegDt().toLocalDate();
                if(now.equals(now2)){                        
                        totalCal += list.get(i).getDfKcal();
                    }
                }
                Assertions.assertEquals(totalCal, 862);
    }


    @Test
    public void getMonthGoal(){
        Integer year = 0;
        Integer month = 0;
        MemberInfoEntity member = mRepo.findByMiSeq(1L);
        List<DayFoodCompleteEntity> entity = dfcRepo.findByMemberOrderByDfcDate(member);
        List<DayFoodCompleteEntity> list = new ArrayList<>();
        for(int i=0 ; i<entity.size(); i++){
            if(year == entity.get(i).getDfcDate().getYear() && month == entity.get(i).getDfcDate().getMonthValue()){
                list.add(entity.get(i));
            }
        }
        System.out.println(list);
    }

    @Test
    public void getDayKcal(){
        MemberInfoEntity member = mRepo.findByMiSeq(1L);
        DayFoodCompleteEntity entity = dfcRepo.findByMemberAndDfcDate(member, LocalDate.of(2023, 02, 10));
        System.out.println(entity);
    }

    @Test
    public void getProgGoal(){
        Integer month = 0;
        MemberInfoEntity member = mRepo.findByMiSeq(1L);
        List<DayFoodCompleteEntity> entity = dfcRepo.findByMemberOrderByDfcDate(member);
        Integer count = 0;
        Integer length = 0;
        for(int i=0; i<entity.size(); i++){
            if(entity.get(i).getDfcDate().getMonth().getValue() == month) {
                 if(entity.get(i).getDfcGoal() == true){
                     count += 1;
                     length = entity.get(i).getDfcDate().lengthOfMonth();
                 }
            }
         }
         Double result = (double)count/length*100;
         String success = String.format("%.2f", result);
         System.out.println(success);
    }
}
