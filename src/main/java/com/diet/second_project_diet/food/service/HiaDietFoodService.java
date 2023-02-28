package com.diet.second_project_diet.food.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.DayFoodCompleteEntity;
import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.MemoInfoEntity;
import com.diet.second_project_diet.food.vo.HiaDataResponseVO;
import com.diet.second_project_diet.food.vo.HiaDayTotalCalVO;
import com.diet.second_project_diet.food.vo.HiaDdayGoalResponseVO;
import com.diet.second_project_diet.food.vo.HiaDetailResponseVO;
import com.diet.second_project_diet.food.vo.HiaGoalResponseVO;
import com.diet.second_project_diet.food.vo.HiaGoalVO;
import com.diet.second_project_diet.food.vo.HiaProgGoalResponseVO;
import com.diet.second_project_diet.food.vo.HiaResponseVO;
import com.diet.second_project_diet.member.service.HiaMemberService;
import com.diet.second_project_diet.memo.service.HiaMemoService;
import com.diet.second_project_diet.repository.DayFoodCompleteRepository;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.MemoInfoRepository;

@Service
public class HiaDietFoodService {
    @Autowired DayFoodCompleteRepository dfcRepo;
    @Autowired DayFoodRepository dfRepo;
    @Autowired MemberInfoRepository mRepo;
    @Autowired HiaMemberService hiaMService;
    @Autowired HiaMemoService hiaMeService;
    @Autowired MemoInfoRepository meRepo;

    // 해당 일 총 칼로리 계산 후 데이터 저장
    public HiaResponseVO sumTotalCal(HiaDayTotalCalVO data, String token){
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        DayFoodCompleteEntity entity = dfcRepo.findByMemberAndDfcDate(member, data.getDay().toLocalDate());
        List<DayFoodEntity> list = dfRepo.findByMember(member);
        LocalDate now = data.getDay().toLocalDate(); 
        HiaResponseVO response = new HiaResponseVO();
        if(member == null){
            response = HiaResponseVO.builder()
            .status(false)
            .message("회원토큰 및 날짜를 확인해주세요.").build();
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
        entity = DayFoodCompleteEntity.builder().member(member).dfcTotalCal(totalCal).dfcDate(data.getDay().toLocalDate()).dfcGoal(false).build();
        dfcRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(true)
            .message("해당 일 총 칼로리가 등록되었습니다.").build();
        }
        return response;
    
    }


    // 년.월 별 목표 달성 여부 출력
    public HiaGoalResponseVO getMonthDietFood(String token, Integer year, Integer month) {
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        HiaGoalResponseVO response = new HiaGoalResponseVO();
        List<DayFoodCompleteEntity> entity = dfcRepo.findByMemberOrderByDfcDate(member);
        List<HiaGoalVO> list = new ArrayList<>();
        HiaGoalVO vo = new HiaGoalVO();
        for(int i=0 ; i<entity.size(); i++){
        if(year == entity.get(i).getDfcDate().getYear() && month == entity.get(i).getDfcDate().getMonthValue()){
            vo = HiaGoalVO.builder()
            .date(entity.get(i).getDfcDate()).goal(entity.get(i).getDfcGoal()).totalCal(entity.get(i).getDfcTotalCal()).build();
            list.add(vo);
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
    public HiaDataResponseVO getDayKcal(String token, LocalDate today){
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        DayFoodCompleteEntity entity = dfcRepo.findByMemberAndDfcDate(member, today);
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

    // 매월 목표 달성도 출력
    public HiaProgGoalResponseVO getProgGoal(String token, Integer month){
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        HiaProgGoalResponseVO response = new HiaProgGoalResponseVO();
        List<DayFoodCompleteEntity> entity = dfcRepo.findByMemberOrderByDfcDate(member);
        Integer count = 0;
        Integer length = 0;
        Integer notCount = 0;
        if(member == null){
            response = HiaProgGoalResponseVO.builder().status(false).message("회원 토큰 정보를 확인해주세요.").build();
        }
        else if(entity.isEmpty()){
            response = HiaProgGoalResponseVO.builder().status(false).message("조회된 정보가 없습니다.").build();
        }
        else{
        for(int i=0; i<entity.size(); i++){
           if(entity.get(i).getDfcDate().getMonth().getValue() == month) {
                if(entity.get(i).getDfcGoal() == true){
                    count += 1;
                    length = entity.get(i).getDfcDate().lengthOfMonth();
                }
           }
           else if(entity.get(i).getDfcDate().getMonth().getValue() != month){
                notCount += 1;              
           }
        }
        if(notCount == entity.size()){
            response = HiaProgGoalResponseVO.builder().status(false).message("등록된 정보 값이 없습니다.").build();
        }
        else{
        Double result = (double)count/length*100;
        String success = String.format("%.2f", result);
        response = HiaProgGoalResponseVO.builder().data(success).status(true).message("달성도가 출력되었습니다.").build();  
        }
    }
        return response;
    }

    // D-day 기준 목표 달성도 출력
    public HiaDdayGoalResponseVO getDdayGoal(String token) throws Exception{
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        List<DayFoodCompleteEntity> list = dfcRepo.findByMemberOrderByDfcDate(member);
        Integer count = 0;
        Integer dDay = 0;
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getDfcGoal() == true){
                count += 1;
                dDay = hiaMService.dday(member.getMiToken()).getDDay();
            }
        }
        Double result = (double)count/dDay*100;
        String success = String.format("%.2f", result);
        HiaDdayGoalResponseVO response = HiaDdayGoalResponseVO.builder()
        .status(true).message("D-day 기준 목표 달성도 출력").data(success).build();
        return response;
    }

    // 오늘의 식단 상세보기
    public HiaDetailResponseVO getDetailDiet(Long dfSeq){
        DayFoodEntity day = dfRepo.findByDfSeq(dfSeq);
        MemoInfoEntity memo = meRepo.findByDay(day);
        HiaDetailResponseVO response = new HiaDetailResponseVO();
        if (day == null) {
            response = HiaDetailResponseVO.builder()
                    .status(false).message("오늘의 식단 번호를 확인해주세요.").build();
        }
        else if (memo == null) {
            response = HiaDetailResponseVO.builder()
            .status(true).message("오늘의 식단 상세보기").data(day).memo(null).build();
        }
        else{
            response = HiaDetailResponseVO.builder()
            .status(true).message("오늘의 식단 상세보기").data(day).memo(memo.getMeiContent()).build();
        }
        return response;
    }
}
