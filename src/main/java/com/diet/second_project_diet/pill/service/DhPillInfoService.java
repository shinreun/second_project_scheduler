package com.diet.second_project_diet.pill.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.PillInfoCompleteEntity;
import com.diet.second_project_diet.entity.PillInfoEntity;
import com.diet.second_project_diet.pill.vo.DhPillInfoInsertVO;
import com.diet.second_project_diet.pill.vo.DhRePillInfoInsertVO;
import com.diet.second_project_diet.pill.vo.DhResponseVO;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.PillInfoRepository;
import com.diet.second_project_diet.repository.PillinfocompleteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DhPillInfoService {
    private final MemberInfoRepository memberRepo;
    private final PillInfoRepository pillRepo;
    private final PillinfocompleteRepository pcRepo;

    // 약 추가 완료 -service 조건문 추가하기
    public DhResponseVO addPillInfo(DhPillInfoInsertVO data, String token) {
        MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
        DhResponseVO response = new DhResponseVO();
        PillInfoEntity entity = PillInfoEntity.builder()
                .piName(data.getPiName())
                .piAmount(data.getPiAmount())
                .piStatus(0)
                .member(member)
                .build();
        pillRepo.save(entity);
        PillInfoCompleteEntity entity2 = PillInfoCompleteEntity.builder()
                .picTotal(0)
                .pill(entity)
                .picGoal(0)
                .picDate(LocalDate.now())
                .build();
        pcRepo.save(entity2);
        response = DhResponseVO.builder()
                .status(false)
                .message("약 정보가 추가되었습니다.").build();
        return response;
    }

    // 수정 - 일단 수정은 되고 조건문 추가하기??
    public DhResponseVO updatePillInfo(Long piSeq, String token, DhRePillInfoInsertVO data) {
        // Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        // 토큰값 찾아오기
        MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
        DhResponseVO response = new DhResponseVO();

        // 이미 존재하는 entity 들고오기
        if (member == null) {
            response = DhResponseVO.builder().status(false)
            .message("로그인 한 회원만 이용 가능합니다.").build();
        }
        // 약 수정할 이름 입력
        PillInfoEntity entity = pillRepo.findByPiSeq(piSeq);
        if (data.getPiName() == null) {
            response = DhResponseVO.builder().status(false)
            .message("약 이름을 입력하세요").build();
        }
        // 약 수정할 갯수 입력
        else if ( data.getPiAmount() == null) {
            response = DhResponseVO.builder().status(false)
            .message("약의 총갯수를 입력하세요").build();
        }
        // 새로운 데이터 입력
        PillInfoEntity newPillEntity = new PillInfoEntity(entity.getPiSeq(), member,
            data.getPiName(), data.getPiAmount(), entity.getPiStatus());
        // 새로운 데이터 저장
        pillRepo.save(newPillEntity);

        response = DhResponseVO.builder()
                .status(false)
                .message("약 정보가 수정되었습니다.").build();
        return response;
    }
    


    // 삭제
    public DhResponseVO deletePillInfo(String token, String piName) {
        MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
        DhResponseVO response = new DhResponseVO();
        if (member == null) {
            response = DhResponseVO.builder().status(false)
            .message("로그인 한 회원만 이용 가능합니다.").build();
        }
        else{
            PillInfoEntity pillEntity = pillRepo.findByPiName(piName);
            if( pillEntity == null) {
                response = DhResponseVO.builder()
                .status(false)
                .message("해당 약은 존재하지 않습니다.").build();
            }
            else if (pillEntity.getMember() != member) {
                response = DhResponseVO.builder()
                .status(false)
                .message("로그인한 회원과 약 등록자가 일치하지 않습니다.").build();
            }
            else {
            pillRepo.delete(pillEntity);

            response = DhResponseVO.builder()
                .status(false)
                .message("약 정보가 삭제되었습니다.").build();
            return response;
            }
        }
        
        response = DhResponseVO.builder()
                .status(false)
                .message("약 정보가 삭제되었습니다.").build();
        return response;
    }





    
    // 출력
    public DhResponseVO getPillInfo(String token) {
        // Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        // 토큰값 찾아오기
        MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
        DhResponseVO response = new DhResponseVO();
        
        response = DhResponseVO.builder()
                .status(false)
                .message("약 정보가 추가되었습니다.").build();
        return response;
    }
    // 빨리빨리 만드세요!! 죄송합니다 ㅠㅠㅠㅠㅠㅠㅠㅠ

}