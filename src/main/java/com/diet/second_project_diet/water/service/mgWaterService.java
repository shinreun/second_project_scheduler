package com.diet.second_project_diet.water.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.WaterInfoEntity;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.WaterInfoRepository;
import com.diet.second_project_diet.water.vo.mgWaterListResponseVO;
import com.diet.second_project_diet.water.vo.mgWaterResponseVO;

import lombok.Builder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;






@Service
//@Builder
public class mgWaterService {
    @Autowired WaterInfoRepository wRepo;
    @Autowired MemberInfoRepository mRepo;
      //  하루 섭취량 수정 - 증가
     public mgWaterResponseVO  updateWater(Long miSeq,  LocalDate date) {
        //Map<String, Object> resultMap = new LinkedHashMap<String, Object>(); // 원래는 맵으로 받지만
        MemberInfoEntity member = mRepo.findByMiSeq(miSeq);
        WaterInfoEntity entity = wRepo.findByMemberAndWiDate(member, date);
        //entity.setWiCount(entity.getWiCount()+1); // 1증가
        if(entity == null){
            mgWaterResponseVO response = mgWaterResponseVO.
            builder() .status(false).message("음수량은 설정할 수 없습니다.").build();
            entity=wRepo.findByWiSeq(miSeq);            
            return response;
            }
        else {
            if( entity.getWiCount() >= 8) {
                    mgWaterResponseVO response = mgWaterResponseVO.
            builder() .status(false).message("최대 음수량을 초과했습니다.").build();
            return response;
            }
            else{
                entity.setWiCount(entity.getWiCount()+1);
                wRepo.save(entity);
                         mgWaterResponseVO response = mgWaterResponseVO.
            builder() .status(true).message("음수량 증가").build();
            return response;
            }
        }
    }
    // 하루 섭취량 수정 - 감소
    public mgWaterResponseVO minusWater(Long miSeq, LocalDate date) {
        //Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        MemberInfoEntity member = mRepo.findByMiSeq(miSeq);
        WaterInfoEntity entity = wRepo.findByMemberAndWiDate(member, date);

        mgWaterResponseVO response = new mgWaterResponseVO();
        if(entity == null){
            response = mgWaterResponseVO.
            builder() .status(false).message("음수량은 설정할 수 없습니다.").build();
            entity=wRepo.findByWiSeq(miSeq);            
           
        }
        else if(entity.getWiCount() >0){
            entity.setWiCount(entity.getWiCount()-1);
            wRepo.save(entity);
            response = mgWaterResponseVO.
            builder() .status(true).message("음수량 감소").build();
           
        }
        return response;
    }
    
    // 내가 하고 싶은 월 ~ 일 음수량 ^조회^
    // public mgWaterListResponseVO waterList(String keyword, Pageable pageable) {
    //     Page<WaterInfoEntity> page = wRepo.findByWiCountContains( keyword, pageable);
    //     //Map<String, Object> map = new LinkedHashMap<String, Object>();
    //      mgWaterResponseVO response = new mgWaterResponseVO();
    //     response = mgWaterResponseVO.builder()
    //     .list(page.getContent())
    //     .total(page.getTotalElements())
    //     .titalPage(page.getTotalPages())
    //     .currentPage(page.getNumber())
    //     .build();
    //     return reponse;


        // map.put("list", page.getContent());
        // map.put("total", page.getTotalElements());
        // map.put("total", page.getTotalPages());
        // map.put("currentPage", page.getNumber());
        // return map;

    //   }
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















      

