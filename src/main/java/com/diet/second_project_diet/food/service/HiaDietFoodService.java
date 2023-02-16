package com.diet.second_project_diet.food.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;
import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.food.vo.HiaDataResponseVO;
import com.diet.second_project_diet.food.vo.HiaDayTotalCalVO;
import com.diet.second_project_diet.food.vo.HiaGoalResponseVO;
import com.diet.second_project_diet.food.vo.HiaProgGoalResponseVO;
import com.diet.second_project_diet.food.vo.HiaResponseVO;
import com.diet.second_project_diet.repository.DayFoodCompleteRepository;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;

@Service
public class HiaDietFoodService {
    @Autowired DayFoodCompleteRepository dfcRepo;
    @Autowired DayFoodRepository dfRepo;
    @Autowired MemberInfoRepository mRepo;
    
    // 해당 일 총 칼로리 계산 후 데이터 저장
    public HiaResponseVO sumTotalCal(HiaDayTotalCalVO data){
        MemberInfoEntity member = mRepo.findByMiSeq(data.getMiSeq());
        DayFoodCompleteEntity entity = dfcRepo.findByDfcMiSeqAndDfcDate(data.getMiSeq(), data.getDay().toLocalDate());
        System.out.println(entity);
        List<DayFoodEntity> list = dfRepo.findByMember(member);
        LocalDate now = data.getDay().toLocalDate(); 
        HiaResponseVO response = new HiaResponseVO();
        if(member == null){
            response = HiaResponseVO.builder()
            .status(false)
            .message("회원번호 및 날짜를 확인해주세요.").build();
        }
        else{
            if(entity != null){
                dfcRepo.delete(entity);
            }
            Integer totalCal = 0;
            for (int  i =0; i< list.size(); i++) {
                LocalDate now2 = list.get(i).getDfRegDt().toLocalDate();
                if(now.equals(now2)){                        
                        totalCal += list.get(i).getDfKcal();
                    }
                }
        entity = DayFoodCompleteEntity.builder().dfcMiSeq(data.getMiSeq()).dfcTotalCal(totalCal).dfcDate(data.getDay().toLocalDate()).dfcGoal(false).build();
        dfcRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(false)
            .message("해당 일 총 칼로리가 등록되었습니다.").build();
        }
        return response;
    
    }


    // 년.월 별 목표 달성 여부 출력
    public HiaGoalResponseVO getMonthDietFood(Long miSeq, Integer year, Integer month){
        HiaGoalResponseVO response = new HiaGoalResponseVO();
        List<DayFoodCompleteEntity> entity = dfcRepo.findByDfcMiSeq(miSeq);
        List<DayFoodCompleteEntity> list = new ArrayList<>();
        for(int i=0 ; i<entity.size(); i++){
        if(year == entity.get(i).getDfcDate().getYear() && month == entity.get(i).getDfcDate().getMonthValue()){
            list.add(entity.get(i));
        }
    }
        if(list.size() == 0){
            response = HiaGoalResponseVO.builder()
            .status(true).message("조회된 정보가 없습니다.").build();
        }
        else{
            response = HiaGoalResponseVO.builder()
            .status(true).message("조회된 정보.").list(list).build(); 
      
        }
        return response;
    }


    // 해당일 실시간 섭취한 칼로리 조회
    public HiaDataResponseVO getDayKcal(Long miSeq, LocalDate today){
        DayFoodCompleteEntity entity = dfcRepo.findByDfcMiSeqAndDfcDate(miSeq, today);
        HiaDataResponseVO response = new HiaDataResponseVO();
        if(entity == null){
            response = HiaDataResponseVO.builder()
            .status(false).message("회원번호 및 날짜를 확인해 주세요.").build();
        }
        else{
            response = HiaDataResponseVO.builder()
            .status(true).message("실시간 총 섭취 칼로리 조회!").data(entity).build();
        }
        return response;
    }

    // 목표 달성도 출력
    public HiaProgGoalResponseVO getProgGoal(Long miSeq, Integer month){
        List<DayFoodCompleteEntity> entity = dfcRepo.findByDfcMiSeq(miSeq);
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
        HiaProgGoalResponseVO response = HiaProgGoalResponseVO.builder().data(success).status(true).message("달성도가 출력되었습니다.").build();
        return response;
    }
}
