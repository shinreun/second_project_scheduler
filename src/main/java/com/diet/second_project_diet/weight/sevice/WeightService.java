package com.diet.second_project_diet.weight.sevice;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.WeightInfoEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.WeightInfoRepository;
import com.diet.second_project_diet.weight.vo.ReDiffWeightResponseVO;
import com.diet.second_project_diet.weight.vo.ReGetWeightResponseByTermVO;
import com.diet.second_project_diet.weight.vo.ReGetWeightResponseVO;
import com.diet.second_project_diet.weight.vo.ReStatusAndMessageResponseVO;
import com.diet.second_project_diet.weight.vo.ReWeightCompareVO;
import com.diet.second_project_diet.weight.vo.ReWeightDifferneceResponseVO;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class WeightService {
  private final MemberInfoRepository memberRepo;
  private final WeightInfoRepository weightRepo;

  // 체중 입력 및 수정
  public ReGetWeightResponseVO addDailyWeight(Double weight, String token) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReGetWeightResponseVO response = new ReGetWeightResponseVO();
    if (member == null) {
      response = ReGetWeightResponseVO.builder().data(null).message("로그인 한 회원만 이용가능합니다.").status(false).build();
    } else {
      WeightInfoEntity data = weightRepo.findByMemberAndWeiDate(member, LocalDate.now());
      if (data != null) {
      data = WeightInfoEntity.builder()
            .member(member)
            .weiDate(LocalDate.now())
            .weiSeq(data.getWeiSeq())
            .weiWeight(weight)
            .build();
        weightRepo.save(data);
        response = ReGetWeightResponseVO.builder().data(data).message("체중 정보를 수정하였습니다.").status(true).build();
      }
      else {
        data = WeightInfoEntity.builder()
            .member(member)
            .weiDate(LocalDate.now())
            .weiSeq(null)
            .weiWeight(weight)
            .build();
        weightRepo.save(data);
        response = ReGetWeightResponseVO.builder().data(data).message("체중이 입력되었습니다.").status(true).build();
      }
    }
    return response;
  }

  // 체중 수정
  public ReGetWeightResponseVO updateDailyWeight(String token, Long weiSeq, Double weight) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReGetWeightResponseVO response = new ReGetWeightResponseVO();
    WeightInfoEntity data = weightRepo.findByMemberAndWeiSeq(member, weiSeq);
    if (member == null) {
      response = ReGetWeightResponseVO.builder().diff(null).data(null).message("로그인 한 회원만 이용가능합니다.").status(false).build();
    } else if (data == null) {
      response = ReGetWeightResponseVO.builder().diff(null).data(null).message("등록된 몸무게가 없습니다.").status(false).build();
    } else {
      data.setWeiWeight(weight);
      weightRepo.save(data);
      List<WeightInfoEntity> list = weightRepo.findByMemberOrderByWeiDate(member);
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i)==data){
          Double difference = list.get(i).getWeiWeight() - list.get(i - 1).getWeiWeight();
          response = ReGetWeightResponseVO.builder().diff(null).data(data).diff(difference).message("체중이 수정되었습니다.").status(true).build();
        }
      }
    }
    return response;
  }

  // 체중 출력 (해당일자)
  public ReGetWeightResponseVO getDailyWeight(String token, LocalDate date) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReGetWeightResponseVO response = new ReGetWeightResponseVO();
    WeightInfoEntity weight = weightRepo.findByMemberAndWeiDate(member, date);
    if (member == null) {
      response = ReGetWeightResponseVO.builder().diff(null).data(null).message("로그인 한 회원만 이용가능합니다.").status(false)
          .build();
    } else if (weight == null) {
      response = ReGetWeightResponseVO.builder().diff(null).data(null).message("등록된 몸무게가 없습니다.").status(false).build();
    } else {
      List<WeightInfoEntity> list = weightRepo.findByMemberOrderByWeiDate(member);
      for (int i = 0; i < list.size(); i++) {
        if (list.get(0) == weight) {
          response = ReGetWeightResponseVO.builder().diff(null).data(weight).diff(0.0).message("체중이 조회되었습니다.")
              .status(true).build();
        }
        else if (list.get(i) == weight) {
          Double difference = list.get(i).getWeiWeight() - list.get(i - 1).getWeiWeight();
          Double diff = Math.ceil(difference*100)/100;
          response = ReGetWeightResponseVO.builder().data(weight).diff(diff).message("체중이 조회되었습니다.")
              .status(true).build();
        }
      }
    }
    return response;
  }
    
  // 체중 삭제
  public ReStatusAndMessageResponseVO deleteDailyWeight(String token, Long weiSeq) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReStatusAndMessageResponseVO response = new ReStatusAndMessageResponseVO();
    WeightInfoEntity data = weightRepo.findByMemberAndWeiSeq(member, weiSeq);
    if (member == null) {
      response = ReStatusAndMessageResponseVO.builder().message("로그인 한 회원만 이용가능합니다.").status(false).build();
    } else if (data == null) {
      response = ReStatusAndMessageResponseVO.builder().message("등록된 몸무게가 없습니다.").status(false).build();
    } else {
      weightRepo.delete(data);
      response = ReStatusAndMessageResponseVO.builder().message("체중이 삭제되었습니다.").status(true).build();
    }
    return response;
  }
  
  // 체중 출력 (해당일자의 주)
  public ReGetWeightResponseByTermVO getWeeklyWeight(String token, LocalDate date) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReGetWeightResponseByTermVO response = new ReGetWeightResponseByTermVO();
    // 등록된 체중의 주차가 같은 것 출력
    List<WeightInfoEntity> list = weightRepo.weightListByWeek(date);
    if (member == null) {
      response = ReGetWeightResponseByTermVO.builder().data(null).message("로그인 한 회원만 이용가능합니다.").status(false).build();
    } else if (list.isEmpty()) {
      response = ReGetWeightResponseByTermVO.builder().data(null).message("등록된 몸무게가 없습니다.").status(false).build();
    } else {
      response = ReGetWeightResponseByTermVO.builder().data(list).message("체중이 조회되었습니다.").status(true).build();
    }
    return response;
  }
  
  // 체중 출력 (해당일자의 달)
  public ReGetWeightResponseByTermVO getMonthlyWeight(String token, LocalDate date) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReGetWeightResponseByTermVO response = new ReGetWeightResponseByTermVO();
    // 등록된 체중의 주차가 같은 것 출력
    List<WeightInfoEntity> list = weightRepo.weightListByMonth(date);
    if (member == null) {
      response = ReGetWeightResponseByTermVO.builder().data(null).message("로그인 한 회원만 이용가능합니다.").status(false).build();
    } else if (list.isEmpty()) {
      response = ReGetWeightResponseByTermVO.builder().data(null).message("등록된 몸무게가 없습니다.").status(false).build();
    } else {
      response = ReGetWeightResponseByTermVO.builder().data(list).message("체중이 조회되었습니다.").status(true).build();
    }
    return response;
  }

  // 체중 변화값 출력
  public ReWeightDifferneceResponseVO getWeightDifference(String token) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    List<WeightInfoEntity> list = weightRepo.findByMemberOrderByWeiDate(member);
    ReWeightDifferneceResponseVO response = new ReWeightDifferneceResponseVO();
    if (member == null) {
      response = ReWeightDifferneceResponseVO.builder().data(null)
          .message("로그인 한 회원만 이용가능합니다.").status(false).build();
    } else if (list == null) {
      response = ReWeightDifferneceResponseVO.builder().data(null)
          .message("등록된 체중이 존재하지 않습니다.").status(false).build();
    } else {
      List<ReDiffWeightResponseVO> diffList = new ArrayList<>();
      for (int i = 0; i < list.size(); i++) {
        if (i == 0) {
          ReDiffWeightResponseVO diff = ReDiffWeightResponseVO.builder().difference(null)
              .weiSeq(list.get(i).getWeiSeq())
              .weiWeight(list.get(i).getWeiWeight()).weiDate(list.get(i).getWeiDate()).build();
          diffList.add(diff);
        } else {
          Double difference = list.get(i).getWeiWeight() - list.get(i - 1).getWeiWeight();
          Double diff2 = Math.ceil(difference*100)/100;
          ReDiffWeightResponseVO diff = ReDiffWeightResponseVO.builder().difference(diff2)
              .weiSeq(list.get(i).getWeiSeq())
              .weiWeight(list.get(i).getWeiWeight()).weiDate(list.get(i).getWeiDate()).build();
          diffList.add(diff);
        }
      }
      response = ReWeightDifferneceResponseVO.builder().data(diffList)
          .message("체중과 변화량이 출력되었습니다.").status(true).build();
    }
    return response;
  }
  
  // 현재무게, 목표무게, 변화량 출력
  public ReWeightCompareVO getWeightCompare(String token) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    List<WeightInfoEntity> list = weightRepo.findByMemberOrderByWeiDate(member);
    ReWeightCompareVO response = new ReWeightCompareVO();
    if (member == null) {
      response = ReWeightCompareVO.builder().diff(null).goalWeight(null).nowWeight(null)
          .message("로그인 한 회원만 이용가능합니다.").status(false).build();
    } else if (list == null) {
      response = ReWeightCompareVO.builder().diff(null).goalWeight(member.getMiGoalKg()).nowWeight(null)
          .message("등록된 체중이 존재하지 않습니다.").status(false).build();
    } else {
      // 날짜 순으로 정렬된 값이니까 마지막 값이 오늘 체중 - 직전 값이 = 변화량, 목표 량은 멤버에서 가져오기
      response = ReWeightCompareVO.builder()
        .diff((list.get(list.size() - 1).getWeiWeight()) - (list.get(list.size() - 2).getWeiWeight()))
          .goalWeight(member.getMiGoalKg())
          .nowWeight(list.get(list.size()-1).getWeiWeight())
        .message("체중이 출력되었습니다.").status(true).build();
     }
    return response;
  }

}
