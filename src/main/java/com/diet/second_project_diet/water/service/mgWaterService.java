package com.diet.second_project_diet.water.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.WaterInfoEntity;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.WaterInfoRepository;
import com.diet.second_project_diet.water.vo.mgWaterResponseVO;
import com.diet.second_project_diet.water.vo.mgWeekListWaterVO;
import com.diet.second_project_diet.water.vo.mgDayWaterVO;
import com.diet.second_project_diet.water.vo.mgWeeklyListVO;

import lombok.Builder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;






@Service
//@Builder
public class mgWaterService {
    @Autowired WaterInfoRepository wRepo;
    @Autowired MemberInfoRepository mRepo;
      //  하루 섭취량 수정 - 증가
     public mgWaterResponseVO  updateWater(String token,  LocalDate date) {
        //Map<String, Object> resultMap = new LinkedHashMap<String, Object>(); // 원래는 맵으로 받지만
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        WaterInfoEntity entity = wRepo.findByMemberAndWiDate(member, date);
        //entity.setWiCount(entity.getWiCount()+1); // 1증가
        if(entity == null){
            mgWaterResponseVO response = mgWaterResponseVO.
            builder() .status(false).message("음수량은 설정할 수 없습니다.").build();
            return response;
            }
        else {
            if( entity.getWiCount() >= 20) {
                    mgWaterResponseVO response = mgWaterResponseVO.
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
                         mgWaterResponseVO response = mgWaterResponseVO.
            builder() .status(true).message("음수량 증가").build();
            return response;
            }
        }
    }
    // 하루 섭취량 수정 - 감소
    public mgWaterResponseVO minusWater(String token, LocalDate date) {
        //Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        WaterInfoEntity entity = wRepo.findByMemberAndWiDate(member, date);

        mgWaterResponseVO response = new mgWaterResponseVO();
        if(entity == null){
            response = mgWaterResponseVO.
            builder() .status(false).message("음수량은 설정할 수 없습니다.").build();
        }
        else if(entity.getWiCount()>0){
            entity.setWiCount(entity.getWiCount()-1);
            if (entity.getWiCount() < member.getMiWater()) {
                entity.setWiGoal(false);
            }
            wRepo.save(entity);
            response = mgWaterResponseVO.
            builder() .status(true).message("음수량 감소").build();
        }
        return response;
    }
    
    // 내가 하고 싶은 월 ~ 일 음수량 ^조회^
    public mgWeeklyListVO weekWaterList(String token, LocalDate date ) { // 여기의 date는 내가 담을 date임 WaterInfo의 date는 아님
        MemberInfoEntity member = mRepo.findByMiTokenIs(token); // 멤버에서 회원번호 받아와
        mgWeeklyListVO response = mgWeeklyListVO.
        builder().status(null).message(null).list(null).build();
        if(member == null) { // member번호가 없다면.
            response = mgWeeklyListVO.
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
                response = mgWeeklyListVO.
                builder().status(false).message("등록된 정보가 없습니다").list(null).build();
            }
            else {
                List<mgWeekListWaterVO> water = new ArrayList<>();
                for (int i=0; i<entity2.size(); i++) {
                    mgWeekListWaterVO water2 = mgWeekListWaterVO.builder().wiDate(entity2.get(i).getWiDate().getDayOfWeek().toString())
                    .wiGoal(entity2.get(i).getWiGoal()).wiCount(entity2.get(i).getWiCount()).build();
                    water.add(water2);
                }
                response = mgWeeklyListVO.
                builder().status(true).message("조회가 완료되었습니다").list(water).build();
            }
        }
            return response;
        }

    // 하루 음수량 ^조회^
    public mgDayWaterVO weekWater(String token, LocalDate date ) { // 여기의 date는 내가 담을 date임 WaterInfo의 date는 아님
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
        //LocalDate.now().getDayOfWeek().toString(); // 요일로 받아내는 법 
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
    }
    
      

    
    //  }
    //  // 섭취량 조회 근데 총 물 개수가 나오는..
    // public Map<String, Object> countWater(Long wiSeq) {
    //     Map<String,Object> resultMap = new LinkedHashMap<String,Object>();
    //     wiSeq = wRepo.count();
    //     resultMap.put("total_water", wiSeq);
    //     resultMap.put("status", true);
    //     resultMap.put("code", HttpStatus.ACCEPTED);
    //     return resultMap;
    // }


// }
//제대로 된 조회를 만들려고 해씀 - 총 음수량
// public Map<String,Object> countWater(Long wiCount) {
//     Map<String, Object> resultMap = new LinkedHashMap<String,Object>();
//     List <WaterInfoEntity> entity = wRepo.countByWiCount(wiCount);
//     resultMap.put("status", true);
//     resultMap.put("message","조회완료되었습니다.");
//     resultMap.put("code", HttpStatus.OK);
//    return resultMap;


// }


  
  
    
  //  }
            // count로 어떻게든 세아려보셈
//     if (adminAccountRepository.countByAiId(id) > 0) { // 다시 회원가입으로 돟ㄹ가임 : 실패 
//         model.addAttribute("id", id);
//         model.addAttribute("name", name);
//         model.addAttribute("status", "duplicated");
//         return "/member/join";
//     }
// }















      

