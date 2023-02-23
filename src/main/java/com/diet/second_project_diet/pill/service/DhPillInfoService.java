package com.diet.second_project_diet.pill.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
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
              .status(true)
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

   public DhListResponseVO getPillInfo(String token) {
      // listResponse 에 VO데이터를 저장함
      DhListResponseVO listResponse = new DhListResponseVO();
      // listResponse2 에 내보내줄 데이터를 지정해줌
      DhListResponseVO2 listResponse2 = new DhListResponseVO2();
      // member에 miSeq 검색결과를 저장
      MemberInfoEntity member = memberRepo.findByMiTokenIs(token);

      // 일치하는 데이터가 pEntity 여기 들어가있음 (member, piStatus)
      List<PillInfoEntity> pEntity = pillRepo.findByMemberAndPiStatus(member, 0);
      // DhListResponseVO2 결과값을 VO2List에 저장
      List<DhListResponseVO2> VO2List = new ArrayList<>();

      // pEntity 가 비어있을 경우 isEmpty로 설정
      if (pEntity.isEmpty()) {
         // 결과값을 listResponse에 저장
         listResponse = DhListResponseVO.builder()
                 .status(false).message("조회된 정보가 없습니다.").build();
      } else {
         // pEntity 갯수(.size)만큼 반복실행
         for (int i = 0; i < pEntity.size(); i++) {
            // 첫번째꺼 완성
            // 반복실행으로 얻은 테이터들을 listResponse2에 저장
            listResponse2 = DhListResponseVO2.builder()
                    .miSeq(pEntity.get(i).getMember().getMiSeq())
                    .pillSeq(pEntity.get(i).getPiSeq())
                    .pillName(pEntity.get(i).getPiName())
                    .pillAmount(pEntity.get(i).getPiAmount())
                    .build();
            // 반복문 다돌면 VO2List에 값이 저장되어 있음
            VO2List.add(listResponse2);
         }
         // 메세지와 데이터들을 listResponse에 저장
         listResponse = DhListResponseVO.builder()
                 .status(true)
                 .message("조회된 데이터 입니다.")
                 // 저장된 데이터를 list에 저장
                 .list(VO2List)
                 .build();
      }
      // 저장된 listResponse을 내보내주기
      return listResponse;
   }

   public DhResponseVO getPillInfo2(LocalDate picDate, String token) {
      // 멤버 찾기
      MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
      DhResponseVO response = new DhResponseVO();
      // 멤버 없을때 처리 나중에 하기

      List<PillInfoEntity> pEntity = pillRepo.findByMemberAndPiStatus(member, 0);
      List<DhListResponseVO2> VO2List = new ArrayList<>();

      if (member == null) {
         response = DhResponseVO.builder()
                 .status(false)
                 .message("등록된 사용자가 아닙니다.")
                 .build();
         return response;
      }
      else if (pEntity.isEmpty()) {
         response = DhResponseVO.builder()
                 .status(false).message("조회된 정보가 없습니다.").build();
      } else {
         Integer a = 0;
         for (int i = 0; i < pEntity.size(); i++) {
            PillInfoEntity pill = pillRepo.findByPiSeq(pEntity.get(i).getPiSeq());
            if (pcRepo.countByPillAndPicGoalAndPicDate(pill, 0, picDate) != 0) {
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

   // 약 하나 먹은갯수 카운트 증가
   public DhResponseVO updatePlusPill(String token, Long seq, LocalDate date) {
      DhResponseVO response = new DhResponseVO();
      MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
      PillInfoEntity pill = pillRepo.findByPiSeq(seq);
      PillInfoCompleteEntity pillComplete = pcRepo.findByPillAndPicDate(pill, date);


      if( member == null) {
         response = DhResponseVO.builder()
                 .status(false)
                 .message("일치하는 회원 정보가없습니다.")
                 .build();
         return response;
      }
      else if (pillComplete == null) {
         response = DhResponseVO.builder()
                 .status(false)
                 .message("설정할 약이 없습니다.").build();
         return response;
      }
      else {
         if (pillComplete.getPicTotal() >= pill.getPiAmount()) {
            response = DhResponseVO.builder()
                    .status(false)
                    .message("일일 복용량을 초과하였습니다")
                    .build();
            return response;
         }
         else {
            // Boolean success = false;
            Integer status = 0;
            if (pillComplete.getPicTotal()+1 >= pill.getPiAmount()) {
               status = 1;
            }
            pillComplete.setPicTotal(pillComplete.getPicTotal()+1);
            pillComplete.setPicGoal(status);
            pcRepo.save(pillComplete);
            response = DhResponseVO.builder()
                    .status(true)
                    .message("복용량이 1 증가하였습니다.")
                    .build();
            return response;
         }
      }
   }

   // 약 하나 먹은갯수 카운트 감소
   public DhResponseVO updateMinusPill(String token, Long seq, LocalDate date) {
      DhResponseVO response = new DhResponseVO();

      MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
      PillInfoEntity pill = pillRepo.findByPiSeq(seq);
      PillInfoCompleteEntity pillComplete = pcRepo.findByPillAndPicDate(pill, date);

      if( member == null) {
         response = DhResponseVO.builder()
                 .status(false)
                 .message("일치하는 회원 정보가없습니다.")
                 .build();
         return response;
      }
      else if (pillComplete == null) {
         response = DhResponseVO.builder()
                 .status(false)
                 .message("설정할 약이 없습니다.").build();
         return response;
      }
      else if(pillComplete.getPicTotal() <= 0){
         response = DhResponseVO.builder()
                 .status(false)
                 .message("약을 복용하지 않았습니다.")
                 .build();
         return response;
      }

      else if (pillComplete.getPicTotal() > 0) {
         pillComplete.setPicTotal(pillComplete.getPicTotal()-1);
         pillComplete.setPicGoal(0);
         pcRepo.save(pillComplete);
         response = DhResponseVO.builder()
                 .status(false)
                 .message("복용량을 1 감소 시켰습니다.")
                 .build();


      }
      return response;
   }
}



















