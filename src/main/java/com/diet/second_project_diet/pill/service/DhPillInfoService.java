package com.diet.second_project_diet.pill.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.PillInfoCompleteEntity;
import com.diet.second_project_diet.entity.PillInfoEntity;
import com.diet.second_project_diet.pill.vo.DhListResponseVO;
import com.diet.second_project_diet.pill.vo.DhListResponseVO2;
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
    public DhResponseVO updatePillInfo(String token, DhRePillInfoInsertVO data, Long piSeq) {
        // Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        // 토큰값 찾아오기
        MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
        DhResponseVO response = new DhResponseVO();
        if (member == null) {
            response = DhResponseVO.builder().status(false)
                    .message("로그인 한 회원만 이용 가능합니다.").build();
        } else {
            PillInfoEntity entity = pillRepo.findByPiSeq(piSeq);
            if (data.getPiName() == null) {
                response = DhResponseVO.builder().status(false)
                        .message("약 이름을 입력하세요").build();
            } else if (data.getPiAmount() == null) {
                response = DhResponseVO.builder().status(false)
                        .message("약의 총갯수를 입력하세요").build();
            }
            PillInfoEntity newPillEntity = new PillInfoEntity(entity.getPiSeq(), member,
                    data.getPiName(), data.getPiAmount(), entity.getPiStatus());
            pillRepo.save(newPillEntity);

            response = DhResponseVO.builder()
                    .status(false)
                    .message("약 정보가 수정되었습니다.").build();
        }
        return response;
    }

    // 삭제
    public DhResponseVO deletePillInfo(String token, Long piSeq, String piName) {
        MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
        DhResponseVO response = new DhResponseVO();
        if (member == null) {
            response = DhResponseVO.builder().status(false)
                    .message("로그인 한 회원만 이용 가능합니다.").build();
        } else {
            PillInfoEntity pillEntity = pillRepo.findByPiSeq(piSeq);
            if (pillEntity == null) {
                response = DhResponseVO.builder()
                        .status(false)
                        .message("약 정보가 존재하지 않습니다.").build();
            } else if (pillEntity.getMember() != member) {
                response = DhResponseVO.builder()
                        .status(false)
                        .message("로그인한 회원과 약 등록자가 일치하지 않습니다.").build();
            } else if (piSeq != pillEntity.getPiSeq() && !piName.equals(pillEntity.getPiName())) {
                response = DhResponseVO.builder()
                        .status(false)
                        .message("약 정보가 일지하지 않습니다.").build();
            } else {
                pillRepo.delete(pillEntity);
                response = DhResponseVO.builder()
                        .status(true)
                        .message("약 정보가 삭제되었습니다.").build();
            }
        }
        return response;
    }

    public DhListResponseVO getPillInfo(Long miSeq) {
        DhListResponseVO listResponse = new DhListResponseVO();
        DhListResponseVO2 listResponse2 = new DhListResponseVO2();
        MemberInfoEntity member = memberRepo.findByMiSeq(miSeq);

        // 일치하는 데이터가 여기 들어가있음
        List<PillInfoEntity> pEntity = pillRepo.findByMemberAndPiStatus(member, 0);
        List<DhListResponseVO2> VO2List = new ArrayList<>();

        if (pEntity.isEmpty()) {
            listResponse = DhListResponseVO.builder()
                    .status(false).message("조회된 정보가 없습니다.").build();

        } else {
            for (int i = 0; i < pEntity.size(); i++) {
//                첫번째꺼 완성
                listResponse2 = DhListResponseVO2.builder()
                        .miSeq(pEntity.get(i).getMember().getMiSeq())
                        .pillSeq(pEntity.get(i).getPiSeq())
                        .pillName(pEntity.get(i).getPiName())
                        .pillAmount(pEntity.get(i).getPiAmount())
                        .build();
//               반복문 다돌면 여기 값이 저장되어 있음
                VO2List.add(listResponse2);
            }
            listResponse = DhListResponseVO.builder()
                    .status(true)
                    .message("조회된 데이터 입니다.")
//                    저장된 데이트를 넣어줌
                    .list(VO2List)
                    .build();
        }
        return listResponse;
    }

    public DhResponseVO getPillInfo2(LocalDate picDate, Long miSeq) {
//       멤버 찾기
        MemberInfoEntity member = memberRepo.findByMiSeq(miSeq);
        DhResponseVO response = new DhResponseVO();
        // 멤버 없을때 처리 나중에 하기

        List<PillInfoEntity> pEntity = pillRepo.findByMemberAndPiStatus(member, 0);
        List<DhListResponseVO2> VO2List = new ArrayList<>();

        if (pEntity.isEmpty()) {
            response = DhResponseVO.builder()
                    .status(false).message("조회된 정보가 없습니다.").build();
        } else {
            Integer a = 0;
            for (int i = 0; i < pEntity.size(); i++) {
                PillInfoEntity pill = pillRepo.findByPiSeq(pEntity.get(i).getPiSeq());
                if (pcRepo.countByPillAndPicGoalAndPicDate(pill, 1, picDate) != 0) {
                    a++;
                }
            }
            if (a != 0) {
                response = DhResponseVO.builder()
                        .status(false)
                        .message("등록하신 약을 모두 섭취하지 않았습니다.")
                        .build();
            } else {
                response = DhResponseVO.builder()
                        .status(true)
                        .message("등록하신 약을 모두 섭취하셨습니다.")
                        .build();
            }
        }
        return response;
    }
}



















