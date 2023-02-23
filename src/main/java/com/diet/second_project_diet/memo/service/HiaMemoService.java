package com.diet.second_project_diet.memo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.MemoInfoEntity;
import com.diet.second_project_diet.memo.vo.HiaAddMemoVO;
import com.diet.second_project_diet.memo.vo.HiaGetMemoVO;
import com.diet.second_project_diet.memo.vo.HiaMemoDataResponseVO;
import com.diet.second_project_diet.memo.vo.HiaMemoResponseVO;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.MemoInfoRepository;

@Service
public class HiaMemoService {
    @Autowired MemoInfoRepository meRepo;
    @Autowired MemberInfoRepository mRepo;
    @Autowired DayFoodRepository dfRepo;

    // 메모 추가 기능
    public HiaMemoResponseVO addMemoInfo(HiaAddMemoVO data){
        DayFoodEntity day = dfRepo.findByDfSeq(data.getDfSeq());
        HiaMemoResponseVO response = new HiaMemoResponseVO();
        if(day == null){
            response = HiaMemoResponseVO.builder()
            .status(false).message("오늘의 식단 번호를 확인해주세요.").build();
        }
        else{
        MemoInfoEntity entity = MemoInfoEntity.builder()
        .meiSeq(null).meiContent(data.getContent()).day(day).build();
        meRepo.save(entity);
        response = HiaMemoResponseVO.builder()
        .status(true).message("메모 등록 완료").build();
        }
        return response;
    }


    // 메모 조회 기능(해당 날짜 받아서)
    public HiaMemoDataResponseVO getMemoInfo(Long dfSeq){
        DayFoodEntity day = dfRepo.findByDfSeq(dfSeq);
        MemoInfoEntity memo = meRepo.findByDay(day);
        HiaGetMemoVO vo = new HiaGetMemoVO();
        HiaMemoDataResponseVO response = new HiaMemoDataResponseVO();
        if(memo == null){
            response = HiaMemoDataResponseVO.builder()
            .status(false).message("조회된 메모 정보가 없습니다.").build();
        }
        else{
            vo = HiaGetMemoVO.builder()
            .content(memo.getMeiContent()).build();
            response = HiaMemoDataResponseVO.builder()
            .status(true).message("메모 정보 조회!").vo(vo).build();
        }
        return response;
    }

    // 메모 수정 기능
    public HiaMemoResponseVO updateMemoInfo(HiaAddMemoVO data){
        DayFoodEntity day = dfRepo.findByDfSeq(data.getDfSeq());
        MemoInfoEntity entity = meRepo.findByDay(day);
        HiaMemoResponseVO response = new HiaMemoResponseVO();
        if(entity == null){
            response = HiaMemoResponseVO.builder()
            .status(false).message("조회된 데이터가 없습니다.").build();
        }
        else{
            entity.setMeiContent(data.getContent());
            meRepo.save(entity);
            response = HiaMemoResponseVO.builder()
            .status(true).message("메모 수정 완료!").build();
        }
        return response;
    }
}
