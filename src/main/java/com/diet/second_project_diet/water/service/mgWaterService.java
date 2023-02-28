package com.diet.second_project_diet.water.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.WaterInfoEntity;
import com.diet.second_project_diet.member.service.HiaMemberService;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.WaterInfoRepository;
import com.diet.second_project_diet.water.vo.mgUpDownResponseVO;
import com.diet.second_project_diet.water.vo.mgWeekListVO;
import com.diet.second_project_diet.water.vo.mgDayWaterVO;

import com.diet.second_project_diet.water.vo.mgMonthListVO;
import com.diet.second_project_diet.water.vo.mgWeekResponseVO;
import com.diet.second_project_diet.water.vo.mgDdayResponseVO;





@Service
//@Builder
public class mgWaterService {
    @Autowired WaterInfoRepository wRepo;
    @Autowired MemberInfoRepository mRepo;
    @Autowired HiaMemberService hiaMemberService;


      //  하루 섭취량 수정 - 증가
    public mgUpDownResponseVO increaseWater(String token,  LocalDate date) {
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        WaterInfoEntity entity = wRepo.findByMemberAndWiDate(member, date);
        if(entity == null){
            entity = WaterInfoEntity.builder().wiDate(date).member(member).wiCount(1).wiGoal(false).build();
            mgUpDownResponseVO response = mgUpDownResponseVO.
            builder() .status(false).message("오늘도 활기차게 물을 마셔봅시다 ! ").build();
            wRepo.save(entity);
            return response;
            }
        else {
            if( entity.getWiCount() >= 20) {
                    mgUpDownResponseVO response = mgUpDownResponseVO.
            builder() .status(false).message("최대 음수량을 초과했습니다.").build();
            return response;
            }
            else{
                Boolean success = false;
                if(entity.getWiCount()+1 >= member.getMiWater()) {
                    success = true;
                }
                entity.setWiCount(entity.getWiCount()+1);
                entity.setWiGoal(success);
                wRepo.save(entity);
                         mgUpDownResponseVO response = mgUpDownResponseVO.
            builder() .status(true).message("음수량 증가").build();
            return response;
            }
        }
    }


    // 하루 섭취량 수정 - 감소
    public mgUpDownResponseVO decreaseWater(String token, LocalDate date) {
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        WaterInfoEntity entity = wRepo.findByMemberAndWiDate(member, date);

        mgUpDownResponseVO response = new mgUpDownResponseVO();
        if(entity == null){
            entity=WaterInfoEntity.builder().wiDate(date).member(member).wiCount(0).wiGoal(false).build();
            response = mgUpDownResponseVO.
            builder() .status(false).message("물을 마시고 눌러주세요~").build();
            wRepo.save(entity);
        }
        else if(entity.getWiCount()>0){
            entity.setWiCount(entity.getWiCount()-1);
            if (entity.getWiCount() < member.getMiWater()) {
                entity.setWiGoal(false);
            }
            wRepo.save(entity);
            response = mgUpDownResponseVO.
            builder() .status(true).message("음수량 감소").build();
        }
        return response;
    }
    
        //요일별 달성여부 조회(주 단위) 
    public mgWeekResponseVO weeklist(String token, LocalDate date ) { 
        MemberInfoEntity member = mRepo.findByMiTokenIs(token); 
        mgWeekResponseVO response = mgWeekResponseVO.
        builder().status(null).message(null).list(null).build();
        if(member == null) { 
            response = mgWeekResponseVO.
            builder().status(false).message("존재하지 않는 회원의 번호입니다").list(null).build();
        }
        else {
            List<WaterInfoEntity> entity = wRepo.findByWeek(date); // WaterInfo에서 member를 찾아
            List<WaterInfoEntity> entity2 = new ArrayList<>();
            for (int i=0; i<entity.size(); i++) {
                    if(entity.get(i).getMember()==member) {
                        WaterInfoEntity a = WaterInfoEntity.builder().member(entity.get(i).getMember()).wiCount(entity.get(i).getWiCount())
                        .wiDate(entity.get(i).getWiDate()).wiGoal(entity.get(i).getWiGoal()).wiSeq(entity.get(i).getWiSeq()).build();
                        entity2.add(a);
                    }
                }

            if(entity2.isEmpty()) { // entity값이 하나라도 없는 경우
                response = mgWeekResponseVO.
                builder().status(false).message("등록된 정보가 없습니다").list(null).build();
            }
            else {
                List<mgWeekListVO> water = new ArrayList<>();
                for (int i=0; i<entity2.size(); i++) {
                    mgWeekListVO water2 = mgWeekListVO.builder().wiDate(entity2.get(i).getWiDate().getDayOfWeek().toString())
                    .wiGoal(entity2.get(i).getWiGoal()).wiCount(entity2.get(i).getWiCount()).build();
                    water.add(water2);
                }
                response = mgWeekResponseVO.
                builder().status(true).message("조회가 완료되었습니다").list(water).build();
            }
        }
            return response;
        }

    // 하루 목표 음수량 조회 + 하루 달성율(%) 조회
    public mgDayWaterVO daylist(String token, LocalDate date ) { // 여기의 date는 내가 담을 date임 WaterInfo의 date는 아님
        MemberInfoEntity member = mRepo.findByMiTokenIs(token); // 멤버에서 회원번호 받아와
        WaterInfoEntity entity = wRepo.findByWiDateAndMember(date,member); // WaterInfo에서 member를 찾아
        mgDayWaterVO response = mgDayWaterVO.
        builder().status(null).message(null).wiCount(null).wiDate(null).wiGoal(null).build();
        if(member == null) { // member번호가 없다면.
            response = mgDayWaterVO.
            builder().status(false).message("존재하지 않는 회원의 번호입니다").wiCount(null).wiDate(null).wiSuccess(null).wiGoal(null).build();
        }
        else if(entity == null) { // entity값이 하나라도 없는 경우
            response = mgDayWaterVO.
            builder().status(false).message("등록된 정보가 없습니다").wiCount(null).wiDate(null).wiGoal(null).wiSuccess(null).build();
        }
        else {
                Double success = ((double)entity.getWiCount()/member.getMiWater())*100.0;
                String result = String.format("%.2f", success);
              response = mgDayWaterVO.
              builder().status(true).message("조회가 완료되었습니다").wiCount(entity.getWiCount())
              .wiDate(entity.getWiDate().getDayOfWeek().toString()).wiGoal(entity.getWiGoal())
              .wiSuccess(result).build();
            }
            return response;
        }
        
        // d-day기준목표달성율과 성공여부 // 인원이가 바까주면 바꾸깅 ! 
    public mgDdayResponseVO ddaylist(String token) throws Exception {
       MemberInfoEntity member = mRepo.findByMiTokenIs(token);
       List<WaterInfoEntity> list = wRepo.findByMember(member);
       Integer sum = 0;
       Integer dday = 0;
       for(int i=0; i<list.size(); i++){
            if(list.get(i).getWiGoal() == true) {
                sum +=1; 
                dday = hiaMemberService.dday(token).getDDay();
            }
       }
       Double result = (double)sum/dday*100;
       String sucess = String.format("%.2f", result);
       mgDdayResponseVO response =mgDdayResponseVO.builder().sucess(sucess).message("목표달성율 출력 완료").Status(true).build();
        return response; 
    }
    
    //  캘린더 물 조회
    public mgMonthListVO monthlist(String token, Integer year, Integer month ) {
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        mgMonthListVO response =new mgMonthListVO();
        List<WaterInfoEntity> entity =wRepo.findByMember(member);
        List<WaterInfoEntity> list = new ArrayList<>();
        for(int i = 0; i<entity.size(); i++) {
        if(year == entity.get(i).getWiDate().getYear() && month ==entity.get(i).getWiDate().getMonthValue()) {
            list.add(entity.get(i));
            }
        }
        if(list.size() ==0) {
            response = mgMonthListVO.builder()
            .status(false).message("조회된 정보가 없습니다.").success("실패").build();
        }
        else{
            response = mgMonthListVO.builder()
            .status(true).message("조회된 정보").success("성공").list(list).build();
        }
        return response;
    }
}
    
    

    
            
        
        
      
        
    

 

    
      

    
   















      

