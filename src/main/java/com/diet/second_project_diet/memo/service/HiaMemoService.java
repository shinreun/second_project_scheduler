package com.diet.second_project_diet.memo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.MemoInfoEntity;
import com.diet.second_project_diet.memo.vo.HiaAddMemoVO;
import com.diet.second_project_diet.memo.vo.HiaGetMemoVO;
import com.diet.second_project_diet.memo.vo.HiaMemoDataResponseVO;
import com.diet.second_project_diet.memo.vo.HiaMemoResponseVO;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.MemoInfoRepository;

@Service
public class HiaMemoService {
    @Autowired MemoInfoRepository meRepo;
    @Autowired MemberInfoRepository mRepo;

    // 메모 추가 기능
    public HiaMemoResponseVO addMemoInfo(HiaAddMemoVO data){
        MemberInfoEntity member = mRepo.findByMiSeq(data.getMiSeq());
        HiaMemoResponseVO response = new HiaMemoResponseVO();
        if(member == null){
            response = HiaMemoResponseVO.builder()
            .status(false).message("회원정보를 확인해주세요.").build();
        }
        else{
        MemoInfoEntity entity = MemoInfoEntity.builder()
        .meiSeq(null).meiContent(data.getContent()).member(member).meiDate(data.getDate()).build();
        meRepo.save(entity);
        response = HiaMemoResponseVO.builder()
        .status(true).message("메모 등록 완료").build();
        }
        return response;
    }

    // 메모 조회 기능(해당 날짜 받아서)
    public HiaMemoDataResponseVO getMemoInfo(Long miSeq, LocalDate day){
        MemberInfoEntity member = mRepo.findByMiSeq(miSeq);
        MemoInfoEntity memo = meRepo.findByMemberAndMeiDate(member, day);
        HiaGetMemoVO vo = new HiaGetMemoVO();
        HiaMemoDataResponseVO response = new HiaMemoDataResponseVO();
        if(memo == null){
            response = HiaMemoDataResponseVO.builder()
            .status(false).message("조회된 메모 정보가 없습니다.").build();
        }
        else{
            vo = HiaGetMemoVO.builder()
            .content(memo.getMeiContent()).day(day).build();
            response = HiaMemoDataResponseVO.builder()
            .status(true).message("메모 정보 조회!").vo(vo).build();
        }
        return response;
    }

    // 메모 수정 기능
    public HiaMemoResponseVO updateMemoInfo(HiaAddMemoVO data){
        MemberInfoEntity member = mRepo.findByMiSeq(data.getMiSeq());
        MemoInfoEntity entity = meRepo.findByMemberAndMeiDate(member, data.getDate());
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
