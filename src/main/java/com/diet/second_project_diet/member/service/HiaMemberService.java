package com.diet.second_project_diet.member.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.MemberInfoEntity;

import com.diet.second_project_diet.member.vo.HiaAddMemberInfoVO;
import com.diet.second_project_diet.member.vo.HiaDataResponseVO;
import com.diet.second_project_diet.member.vo.HiaResponseVO;
import com.diet.second_project_diet.member.vo.HiaTimeResponseVO;
import com.diet.second_project_diet.member.vo.HiaUpdateMemberInfoVO;
import com.diet.second_project_diet.repository.MemberInfoRepository;

import net.bytebuddy.asm.Advice.Local;


@Service
public class HiaMemberService {
    @Autowired MemberInfoRepository mRepo;

    // 회원정보 조회 기능
    public HiaDataResponseVO getMemberInfo(Long seq){
        MemberInfoEntity entity = mRepo.findByMiSeq(seq);
        HiaDataResponseVO response = new HiaDataResponseVO();
        if(entity == null){
            response = HiaDataResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else{
            response = HiaDataResponseVO.builder()
            .status(true).message("회원정보 조회").data(entity).build();
        }
        return response;
    }

    // 회원정보(회원가입) 입력 기능
    public HiaResponseVO addMemberInfo(HiaAddMemberInfoVO data){
        HiaResponseVO response = new HiaResponseVO();
        if(data.getId() == null || data.getId().equals("")){
            response = HiaResponseVO.builder()
            .status(false).message("아이디를 입력하세요.").build();
        }
        else if(mRepo.countByMiId(data.getId()) != 0){
            response = HiaResponseVO.builder()
            .status(false).message(data.getId()+"은/는 이미 사용중입니다.").build();
        }
        else if(data.getName() == null || data.getName().equals("")){
            response = HiaResponseVO.builder()
            .status(false).message("이름을 입력하세요.").build();
        }
        else if(data.getPwd() == null || data.getPwd().equals("")){
            response = HiaResponseVO.builder()
            .status(false).message("비밀번호를 입력하세요.").build();
        }
        else if(data.getBirth() == null){
            response = HiaResponseVO.builder()
            .status(false).message("생년월일을 입력하세요.").build();
        }
        else if(data.getGen() == null){
            response = HiaResponseVO.builder()
            .status(false).message("성별을 입력하세요.").build();
        }
        else if(data.getHard() == null){
            response = HiaResponseVO.builder()
            .status(false).message("다이어트 강도를 입력하세요.").build();
        }
        else if(data.getCal() == null){
            response = HiaResponseVO.builder()
            .status(false).message("목표 칼로리를 입력하세요.").build();
        }
        else if(data.getWater() == null){
            response = HiaResponseVO.builder()
            .status(false).message("목표 음수량을 입력하세요.").build();
        }
        else if(data.getTime() == null){
            response = HiaResponseVO.builder()
            .status(false).message("목표 기간을 입력하세요.").build();
        }
        else if(data.getToken() == null || data.getToken().equals("")){
            response = HiaResponseVO.builder()
            .status(false).message("토큰정보를 입력하세요.").build();
        }
        else {
            LocalDate endTime = LocalDate.now().plusDays(data.getTime());
            MemberInfoEntity entity = MemberInfoEntity.builder()
            .miId(data.getId()).miPwd(data.getPwd()).miName(data.getName()).miBirth(data.getBirth())
            .miGen(data.getGen()).miAddress(data.getAddress()).miStatus(0).miTall(data.getTall()).miWeight(data.getWeight()).miEndTime(endTime)
            .miHard(data.getHard()).miKcal(data.getCal()).miWater(data.getWater()).miStartTime(LocalDate.now()).miToken(data.getToken()).build();
            mRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(true).message("회원정보 등록 완료").build();
        }
        return response;
    }

    // 목표 칼로리 수정
    public HiaResponseVO updateGoalKcal(HiaUpdateMemberInfoVO data){
        Optional<MemberInfoEntity> entity = mRepo.findById(data.getSeq());
        HiaResponseVO response = new HiaResponseVO();
        if(entity.isEmpty()){
            response = HiaResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else{
            MemberInfoEntity m = entity.get();
            m.setMiKcal(data.getCal());
            mRepo.save(m);
            response = HiaResponseVO.builder()
            .status(true).message("목표 칼로리가 변경되었습니다.").build();
        }
        return response;
    }

    // 목표 음수량 수정
    public HiaResponseVO updateGoalWater(HiaUpdateMemberInfoVO data){
        Optional<MemberInfoEntity> entity = mRepo.findById(data.getSeq());
        HiaResponseVO response = new HiaResponseVO();
        if(entity.isEmpty()){
            response = HiaResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else{
            MemberInfoEntity m = entity.get();
            m.setMiWater(data.getWater());
            mRepo.save(m);
            response = HiaResponseVO.builder()
            .status(true).message("목표 음수량이 변경되었습니다.").build();
        }
        return response;
    }

    // 목표 날짜 수정
    public HiaResponseVO updateGoalDay(HiaUpdateMemberInfoVO data){
        MemberInfoEntity entity = mRepo.findByMiSeq(data.getSeq());
        HiaResponseVO response = new HiaResponseVO();
        if(entity == null){
            response = HiaResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else {
            if (LocalDate.now().isAfter(entity.getMiEndTime())) {
                entity.setMiStartTime(LocalDate.now());
                entity.setMiEndTime(LocalDate.now().plusDays(data.getTime()));
                mRepo.save(entity);
                response = HiaResponseVO.builder()
                        .status(true).message("목표 날짜가 변경되었습니다.").build();
            }
            else {
                entity.setMiEndTime(entity.getMiStartTime().plusDays(data.getTime()));
                mRepo.save(entity);
                response = HiaResponseVO.builder()
                        .status(true).message("목표 날짜가 변경되었습니다.").build();
            }
        }
        return response;
    }

    // 회원 탈퇴
    public HiaResponseVO deleteMember(HiaUpdateMemberInfoVO data){
        MemberInfoEntity entity = mRepo.findByMiSeq(data.getSeq());
        HiaResponseVO response = new HiaResponseVO();
        if(entity == null){
            response = HiaResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else if(!entity.getMiPwd().equals(data.getPwd())){
            response = HiaResponseVO.builder()
            .status(false).message("비밀번호가 맞지 않습니다.").build();
        }
        else{
            entity.setMiStatus(2);
            mRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(true).message("회원 탈퇴가 완료되었습니다.").build();
        }
        return response;
    }

    // D-Day 계산하기
    public HiaTimeResponseVO dday(Long seq) throws Exception{
        MemberInfoEntity entity = mRepo.findByMiSeq(seq); 
        HiaTimeResponseVO response = new HiaTimeResponseVO();
        
        if(entity == null){
            response = HiaTimeResponseVO.builder()
            .status(false).message("회원정보를 확인해주세요.").build();
        }
        else{
            String strDate = entity.getMiStartTime().toString();
            String todayFm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(format.parse(strDate).getTime()); 
            Date today = new Date(format.parse(todayFm).getTime());
            Long calculate = date.getTime() - today.getTime();
            int Ddays = (int) (calculate / ( 24*60*60*1000));


            response = HiaTimeResponseVO.builder()
            .status(true).message("D-Day 정보 출력!").dDay(Ddays).build();
        }
        return response;
    }
}
