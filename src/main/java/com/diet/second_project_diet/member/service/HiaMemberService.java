package com.diet.second_project_diet.member.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diet.second_project_diet.entity.MemberInfoEntity;

import com.diet.second_project_diet.member.vo.HiaAddMemberInfoVO;
import com.diet.second_project_diet.member.vo.HiaDataResponseVO;
import com.diet.second_project_diet.member.vo.HiaResponseVO;
import com.diet.second_project_diet.member.vo.HiaTimeResponseVO;
import com.diet.second_project_diet.member.vo.ReLoginRequestVO;
import com.diet.second_project_diet.member.vo.ReLoginVO;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.utils.AESAlgorithm;



@Service
public class HiaMemberService {
    @Autowired MemberInfoRepository mRepo;
    @Autowired HiaFileService fileService;
    // 회원정보 조회 기능
    public HiaDataResponseVO getMemberInfo(String token){
        MemberInfoEntity entity = mRepo.findByMiTokenIs(token);
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
    public HiaResponseVO addMemberInfo(HiaAddMemberInfoVO data, MultipartFile file) {
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
        // else if(data.getAge() == null){
        //     response = HiaResponseVO.builder()
        //     .status(false).message("나이를 입력하세요.").build();
        // }
        // else if(data.getGen() == null){
        //     response = HiaResponseVO.builder()
        //     .status(false).message("성별을 입력하세요.").build();
        // }
        else if(data.getHard() == null){
            response = HiaResponseVO.builder()
            .status(false).message("다이어트 강도를 입력하세요.").build();
        }
        else if(data.getCal() == null){
            response = HiaResponseVO.builder()
            .status(false).message("목표 칼로리를 입력하세요.").build();
        }
        else if(data.getCal() == null){
            response = HiaResponseVO.builder()
            .status(false).message("목표 몸무게를 입력하세요.").build();
        }
        else if(data.getWater() == null){
            response = HiaResponseVO.builder()
            .status(false).message("목표 음수량을 입력하세요.").build();
        }
        else if(data.getTime() == null){
            response = HiaResponseVO.builder()
            .status(false).message("목표 기간을 입력하세요.").build();
        }
        else {
            String saveFilePath = "";
            try {
              saveFilePath = fileService.saveImageFile(file);
            } catch (Exception e) {
             response = HiaResponseVO.builder()
               .status(false).message("파일 전송에 실패했습니다.").build();
            }
            String AESPwd = "";
            try{
                AESPwd = AESAlgorithm.Encrypt(data.getPwd());

            }
            catch (Exception e) {
                response = HiaResponseVO.builder()
                        .status(false).message("비밀번호 암호화에 실패했습니다.").build();
            }
            // 숫자 0부터 알파벳 z까지 제한을 걸고 길이가 20짜리 랜덤 문자열 생성
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 20;
            Random random = new Random();
                    
            String randomString = random.ints(leftLimit,rightLimit + 1)
              .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
              .limit(targetStringLength)
              .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
              .toString();
            LocalDate endTime = LocalDate.now().plusDays(data.getTime());
            MemberInfoEntity entity = MemberInfoEntity.builder()
            .miId(data.getId()).miPwd(AESPwd).miName(data.getName()).miAge(data.getAge()).miImg(saveFilePath)
            .miGen(data.getGen()).miAddress(data.getAddress()).miStatus(0).miTall(data.getTall()).miWeight(data.getWeight()).miEndTime(endTime)
            .miHard(data.getHard()).miKcal(data.getCal()).miGoalKg(data.getKg()).miWater(data.getWater())
            .miStartTime(LocalDate.now()).miToken(randomString).build();
            mRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(true).message("회원정보 등록 완료").build();
        }
        return response;
    }

    // 목표 칼로리 수정
    public HiaResponseVO updateGoalKcal(Integer cal, String token){
       MemberInfoEntity entity = mRepo.findByMiTokenIs(token);
        HiaResponseVO response = new HiaResponseVO();
        if(entity == null){
            response = HiaResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else{
            entity.setMiKcal(cal);
            mRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(true).message("목표 칼로리가 변경되었습니다.").build();
        }
        return response;
    }

    // 목표 몸무게 수정
    public HiaResponseVO updateGoalKg(Double weight, String token){
        MemberInfoEntity entity = mRepo.findByMiTokenIs(token);
        HiaResponseVO response = new HiaResponseVO();
        if(entity == null){
            response = HiaResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else{
            entity.setMiGoalKg(weight);
            mRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(true).message("목표 몸무게가 변경되었습니다.").build();
        }
        return response;
    }

    // 목표 음수량 수정
    public HiaResponseVO updateGoalWater(Integer water, String token){
        MemberInfoEntity entity = mRepo.findByMiTokenIs(token);
        HiaResponseVO response = new HiaResponseVO();
        if(entity == null){
            response = HiaResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else{           
            entity.setMiWater(water);
            mRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(true).message("목표 음수량이 변경되었습니다.").build();
        }
        return response;
    }

    // 목표 날짜 수정
    public HiaResponseVO updateGoalDay(Integer time, String token){
        MemberInfoEntity entity = mRepo.findByMiTokenIs(token);
        HiaResponseVO response = new HiaResponseVO();
        if(entity == null){
            response = HiaResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else {
            if (LocalDate.now().isAfter(entity.getMiEndTime())) {
                entity.setMiStartTime(LocalDate.now());
                entity.setMiEndTime(LocalDate.now().plusDays(time));
                mRepo.save(entity);
                response = HiaResponseVO.builder()
                        .status(true).message("목표 날짜가 변경되었습니다.").build();
            }
            else {
                entity.setMiEndTime(entity.getMiStartTime().plusDays(time));
                mRepo.save(entity);
                response = HiaResponseVO.builder()
                        .status(true).message("목표 날짜가 변경되었습니다.").build();
            }
        }
        return response;
    }

    // 다이어트 강도 수정
    public HiaResponseVO updateHard(Integer hard, String token){
        MemberInfoEntity entity = mRepo.findByMiTokenIs(token);
        HiaResponseVO response = new HiaResponseVO();
        if(entity == null){
            response = HiaResponseVO.builder()
            .status(false).message("회원번호를 확인해주세요.").build();
        }
        else{
            entity.setMiHard(hard);
            mRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(true).message("다이어트 강도가 변경되었습니다.").build();
        }
        return response;
    } 

    // 회원 탈퇴
    public HiaResponseVO deleteMember(String token){
        MemberInfoEntity entity = mRepo.findByMiTokenIs(token);
        HiaResponseVO response = new HiaResponseVO();
        // if(entity == null){
        //     response = HiaResponseVO.builder()
        //     .status(false).message("회원번호를 확인해주세요.").build();
        // }
        // else if(!entity.getMiPwd().equals(data.getPwd())){
        //     response = HiaResponseVO.builder()
        //     .status(false).message("비밀번호가 맞지 않습니다.").build();
        // }
        // else{
            entity.setMiStatus(2);
            mRepo.save(entity);
            response = HiaResponseVO.builder()
            .status(true).message("회원 탈퇴가 완료되었습니다.").build();
        // }
        return response;
    }

    // D-Day 계산하기
    public HiaTimeResponseVO dday(String token) throws Exception{
        MemberInfoEntity entity = mRepo.findByMiTokenIs(token); 
        HiaTimeResponseVO response = new HiaTimeResponseVO();
        
        if(entity == null){
            response = HiaTimeResponseVO.builder()
            .status(false).message("회원정보를 확인해주세요.").build();
        }
        else{
            String endDate = entity.getMiEndTime().toString();
            String todayFm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(format.parse(endDate).getTime()); 
            Date today = new Date(format.parse(todayFm).getTime());
            Long calculate = date.getTime() - today.getTime();
            int Ddays = (int) (calculate / ( 24*60*60*1000));


            response = HiaTimeResponseVO.builder()
            .status(true).message("D-Day 정보 출력!").dDay(Ddays).build();
        }
        return response;
    }

    // 회원 이미지 수정
    public HiaResponseVO updateImgFile(MultipartFile file, String token) {
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        HiaResponseVO response = new HiaResponseVO();
        String saveFilePath = "";
        try {
            saveFilePath = fileService.saveImageFile(file);
            member.setMiImg(saveFilePath);
            mRepo.save(member);
            response = HiaResponseVO.builder()
                    .status(true).message("파일 등록 완료").build();
        } catch (Exception e) {
            response = HiaResponseVO.builder()
                    .status(false).message("파일 전송에 실패했습니다.").build();
        }
        return response;
    }

    public ReLoginVO Login(ReLoginRequestVO data) {
        ReLoginVO response = new ReLoginVO();
        try{
            MemberInfoEntity member = mRepo.findByMiIdAndMiPwd(data.getId(), AESAlgorithm.Encrypt(data.getPwd()));
            if (member == null) {
                response = ReLoginVO.builder().data(null).message("로그인에 실패하셨습니다.").status(false).build();
            }
            else {
                response = ReLoginVO.builder().data(member).message("로그인 성공").status(true).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    // 로그인 기능
    public HiaResponseVO login(String token){
        HiaResponseVO response = new HiaResponseVO();
        MemberInfoEntity member = mRepo.findByMiTokenIs(token);
        if(member == null){
            response = HiaResponseVO.builder()
            .status(false).message("회원 토큰 정보와 일치하는 정보가 없어 로그인에 실패하였습니다.").build();
        }
        else if(member.getMiStatus() == 1){
            response = HiaResponseVO.builder()
            .status(false).message("해당 계정은 사용 대기 상태라 사용불가 합니다.").build();
        }
        else if(member.getMiStatus() == 2){
            response = HiaResponseVO.builder()
            .status(false).message("해당 계정은 사용 정지 상태라 사용불가 합니다.").build();
        }
        else{
            response = HiaResponseVO.builder()
            .status(true).message("로그인 되었습니다.").build();
        }
        return response;
    }
}
