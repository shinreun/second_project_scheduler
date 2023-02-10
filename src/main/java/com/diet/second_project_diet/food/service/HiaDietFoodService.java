package com.diet.second_project_diet.food.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;
import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.food.vo.HiaDayTotalCalVO;
import com.diet.second_project_diet.repository.DayFoodCompleteRepository;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;

@Service
public class HiaDietFoodService {
    @Autowired DayFoodCompleteRepository dfcRepo;
    @Autowired DayFoodRepository dfRepo;
    @Autowired MemberInfoRepository mRepo;
    
    // 하루 총 칼로리 계산
    // public Map<String, Object> sumTotalCal(HiaDayTotalCalVO data){
    //     Map<String, Object> map = new LinkedHashMap<String, Object>();
    //     DayFoodCompleteEntity entity = new DayFoodCompleteEntity();
    //     MemberInfoEntity member = mRepo.findByMiSeq(data.getMiSeq());
    //     DayFoodEntity day = dfRepo.findByMemberAndDfRegDt(member, data.getDay());
    //     if(dfcRepo.countByMemberAndDfcDate(member, data.getDay()) != 1){
    //         entity.setDfcTotalCal(day.getDfKcal());
    //         dfcRepo.save(entity);
    //     }
    //     else if(dfcRepo.countByMemberAndDfcDate(member, data.getDay()) >= 1){
    //         dfcRepo.deleteByMemberAndDfcDate(member, data.getDay());
    //         dfcRepo.insertData(day);
    //         dfcRepo.save(entity);
    //     }
    //     map.put("status", true);
    //     map.put("message", "해당 일 총 칼로리가 등록되었습니다.");
    //     map.put("code", HttpStatus.OK);
    //     return map;
    // }

    public Map<String, Object> sumTotalCal(HiaDayTotalCalVO data){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        MemberInfoEntity member = mRepo.findByMiSeq(data.getMiSeq());
        List<DayFoodEntity> list = dfRepo.findByMemberAndDfRegDt(member, data.getDay());
        Integer totalCal = 0;
        for (int  i =0; i< list.size(); i++) {
            totalCal += list.get(i).getDfKcal();
        }
        DayFoodCompleteEntity entity = DayFoodCompleteEntity.builder().dfcTotalCal(totalCal).dfcDate(data.getDay()).dfcGoal(0).build();
        dfcRepo.save(entity);
        map.put("status", true);
        map.put("message", "해당 일 총 칼로리가 등록되었습니다.");
        map.put("code", HttpStatus.OK);
        return map;
    }


}
