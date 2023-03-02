package com.diet.second_project_diet.food.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.DietSuggestEntity;
import com.diet.second_project_diet.entity.DayFoodCompleteEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.MemoInfoEntity;
import com.diet.second_project_diet.food.vo.ReDayFoodCompleteResponseVO;
import com.diet.second_project_diet.food.vo.ReDayFoodCompleteVO;
import com.diet.second_project_diet.food.vo.ReDietCalorieExInsertVO;
import com.diet.second_project_diet.food.vo.ReDietCalorieInsertResponseVO;
import com.diet.second_project_diet.food.vo.ReDietCalorieResponseVO;
import com.diet.second_project_diet.food.vo.ReDietInsertResponseVO;
import com.diet.second_project_diet.food.vo.ReDietInsertVO;
import com.diet.second_project_diet.food.vo.ReDietSuggestInsertVO;
import com.diet.second_project_diet.food.vo.ReDietSuggestResponseVO;
import com.diet.second_project_diet.food.vo.ReDietSuggestWeekResponseVO;
import com.diet.second_project_diet.food.vo.ReDietSuggestWeeklyFinalVO;
import com.diet.second_project_diet.food.vo.ReGetDailyDietResponseVO;
import com.diet.second_project_diet.food.vo.ReStatusAndMessageResponseVO;
import com.diet.second_project_diet.entity.DietCalorieExEntity;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.DietSuggestRepository;
import com.diet.second_project_diet.repository.DayFoodCompleteRepository;
import com.diet.second_project_diet.repository.DietCalorieExRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.MemoInfoRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ReDietService {
  @Value("${file.image.dailydiet}") String diet_image_path;
  private final MemberInfoRepository memberRepo;
  private final DayFoodRepository dietRepo;
  private final ReFileService fileService;
  private final DayFoodCompleteRepository dietCompRepo;
  private final DietSuggestRepository suggestRepo;
  private final DietCalorieExRepository calRepo;
  private final MemoInfoRepository memoRepo;
  
  // 식단 추가
  public ReStatusAndMessageResponseVO addDailyDiet(ReDietInsertVO data, MultipartFile file, String token) 
  { MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReStatusAndMessageResponseVO response = new ReStatusAndMessageResponseVO();
    if (member == null) {
       response = ReStatusAndMessageResponseVO.builder()
      .status(false).message("로그인 후 이용 가능한 서비스입니다.")
      .build();
    } else {
      String saveFilePath = "";
      try {
        saveFilePath = fileService.saveImageFile(file);
      } catch (Exception e) {
      response = ReStatusAndMessageResponseVO.builder()
      .status(false).message("파일 전송에 실패했습니다.")
      .build();
      }
      LocalDateTime today = LocalDateTime.now();
      DayFoodEntity entity = new DayFoodEntity(null, member, data.getMenu(), saveFilePath, today, data.getKcal());
      dietRepo.save(entity);

      if (data.getContent() != null) {
      MemoInfoEntity entity2 = MemoInfoEntity.builder()
      .meiSeq(null).meiContent(data.getContent()).day(entity).build();
        memoRepo.save(entity2);
      }

      List<DayFoodEntity> list = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(), entity.getDfRegDt());
      DayFoodCompleteEntity total = dietCompRepo.findByMiSeqAndDfcRegDt(member.getMiSeq(), entity.getDfRegDt());
      if (total == null) {
        Boolean success = true;
        if (member.getMiKcal() < entity.getDfKcal()) {
          success = false;
          success = false;
        }
        DayFoodCompleteEntity comp = DayFoodCompleteEntity.builder()
            .dfcSeq(null)
            .dfcTotalCal(entity.getDfKcal())
            .member(member)
            .dfcGoal(success)
            .dfcDate(LocalDate.of(entity.getDfRegDt().getYear(),entity.getDfRegDt().getMonth(), entity.getDfRegDt().getDayOfMonth()))
            .build();
        dietCompRepo.save(comp);
      }
      else {
        Integer totalCal = 0;
        for (int i = 0; i < list.size(); i++) {
          totalCal += list.get(i).getDfKcal();
        }
        Boolean success = true;
        if (member.getMiKcal() < totalCal) {
          success = false;
        }
        total.setDfcTotalCal(totalCal);
        total.setDfcGoal(success);
        dietCompRepo.save(total);
      }
      response = ReStatusAndMessageResponseVO.builder()
      .status(true).message("식단이 등록되었습니다.")
      .build();
    }
    return response;
  }

  // 식단 출력 (날짜별)
  public ReGetDailyDietResponseVO getDailyDiet(String token, LocalDateTime date) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReGetDailyDietResponseVO response = new ReGetDailyDietResponseVO();
    if (member == null) {
       response = ReGetDailyDietResponseVO.builder().status(false).message("등록되지 않은 회원 정보 입니다.")
            .list(null).build();
    } else {
      List<DayFoodEntity> diet = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(), date);
      if (diet.isEmpty()) {
        response = ReGetDailyDietResponseVO.builder().status(false).message("등록된 식단이 없습니다.")
            .list(null).build();
      } else {
        response = ReGetDailyDietResponseVO.builder().status(true).message(date + " 의 식단입니다.")
            .list(diet).build();
      }
    }
    return response;
  }
  
  // 식단 수정
  public ReStatusAndMessageResponseVO updateDailyDiet(Long dfSeq, LocalDateTime date, MultipartFile file, String token, ReDietInsertVO data) 
  {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReStatusAndMessageResponseVO response = new ReStatusAndMessageResponseVO();
    if (member == null) {
      response = ReStatusAndMessageResponseVO.builder().status(false)
      .message("로그인 한 회원만 이용가능합니다.").build();
    } else {
      // 이미 존재하는 entity 들고오기
      DayFoodEntity entity = dietRepo.findByDfSeq(dfSeq);
      if (entity == null) {
        response = ReStatusAndMessageResponseVO.builder().status(false)
            .message("존재하지 않는 식단 번호입니다.").build();
      }
      else {
        String saveFilePath = "";
        if (file == null) {
          saveFilePath = entity.getDfImg();
        }
        try {
          saveFilePath = fileService.saveImageFile(file);
        } catch (Exception e) {
          response = ReStatusAndMessageResponseVO.builder().status(false)
          .message("파일 전송에 실패했습니다.").build();
        }
        if (data.getMenu() == null) {
          response = ReStatusAndMessageResponseVO.builder().status(false)
          .message("메뉴 이름을 입력하세요.").build();
        } else if (data.getKcal() == null) {
          response = ReStatusAndMessageResponseVO.builder().status(false)
          .message("해당 칼로리를 입력하세요.").build();
        } else if (date == null) {
          // 날짜 정보가 없는 경우, 식단 입력
          LocalDateTime today = LocalDateTime.now();
          DayFoodEntity newEntity = new DayFoodEntity(entity.getDfSeq(), member, data.getMenu(), saveFilePath, today,
          data.getKcal());
          dietRepo.save(newEntity);
          MemoInfoEntity memo = memoRepo.findByDay(newEntity);
          // 메모 입력
          if (memo != null) {
          memo = MemoInfoEntity.builder()
              .meiSeq(memo.getMeiSeq()).meiContent(data.getContent()).day(entity).build();
            memoRepo.save(memo);
          }
          else if (memo == null && data.getContent() != null) {
            MemoInfoEntity entity2 = MemoInfoEntity.builder()
            .meiSeq(null).meiContent(data.getContent()).day(entity).build();
            memoRepo.save(entity2);
          }
            // 전체 칼로리 수정
          List<DayFoodEntity> list = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(), entity.getDfRegDt());
          Integer totalCal = 0;
          for (int i = 0; i < list.size(); i++) {
            totalCal += list.get(i).getDfKcal();
          }
          Boolean success = true;
          if (member.getMiKcal() < totalCal) {
            success = false;
          }
          DayFoodCompleteEntity total = dietCompRepo.findByMiSeqAndDfcRegDt(member.getMiSeq(), entity.getDfRegDt());
          total.setDfcTotalCal(totalCal);
          total.setDfcGoal(success);
          dietCompRepo.save(total);
          response = ReStatusAndMessageResponseVO.builder().status(true)
          .message("식단이 수정되었습니다.").build();
        } else {
        // 식단 수정
        DayFoodEntity newEntity = new DayFoodEntity(entity.getDfSeq(), member, data.getMenu(), saveFilePath, date,
        data.getKcal());
        dietRepo.save(newEntity);
        MemoInfoEntity memo = memoRepo.findByDay(newEntity);
        // 메모 입력
        if (memo != null) {
          memo = MemoInfoEntity.builder()
              .meiSeq(memo.getMeiSeq()).meiContent(data.getContent()).day(entity).build();
          memoRepo.save(memo);
        }
        else if (memo == null && data.getContent() != null) {
          MemoInfoEntity entity2 = MemoInfoEntity.builder()
          .meiSeq(null).meiContent(data.getContent()).day(entity).build();
          memoRepo.save(entity2);
        }
        // 총 칼로리 수정 
        List<DayFoodEntity> list = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(), entity.getDfRegDt());
        Integer totalCal = 0;
        for (int i = 0; i < list.size(); i++) {
          totalCal += list.get(i).getDfKcal();
        }
        Boolean success = true;
        if (member.getMiKcal() < totalCal) {
          success = false;
        }
        DayFoodCompleteEntity total = dietCompRepo.findByMiSeqAndDfcRegDt(member.getMiSeq(), entity.getDfRegDt());
        total.setDfcTotalCal(totalCal);
        total.setDfcGoal(success);
        dietCompRepo.save(total);
        
        response = ReStatusAndMessageResponseVO.builder().status(true)
        .message("식단이 수정되었습니다.").build();
      }
    }
  }
    return response;
  }
  
  // 식단 삭제
  public ReStatusAndMessageResponseVO deleteDailyDiet(String token, Long dfSeq) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReStatusAndMessageResponseVO response = new ReStatusAndMessageResponseVO();
    if (member == null) {
      response = ReStatusAndMessageResponseVO.builder()
      .status(false).message("로그인 후 이용 가능한 서비스입니다.")
      .build();
    } else {
      DayFoodEntity entity = dietRepo.findByDfSeq(dfSeq);
      if (entity == null) {
        response = ReStatusAndMessageResponseVO.builder()
        .status(false).message("해당 식단은 존재하지 않습니다.")
        .build();
      } else if (entity.getMember() != member) {
        response = ReStatusAndMessageResponseVO.builder()
        .status(false).message("로그인 한 회원과 식단 작성자가 일치하지 않습니다.")
        .build();
      } else {
        MemoInfoEntity memo = memoRepo.findByDay(entity);
        if (memo != null) {
          memoRepo.delete(memo);
        }
        dietRepo.delete(entity);

        List<DayFoodEntity> list = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(), entity.getDfRegDt());
        Integer totalCal = 0;
        for (int i = 0; i < list.size(); i++) {
          totalCal += list.get(i).getDfKcal();
        }
        Boolean success = true;
        if (member.getMiKcal() < totalCal) {
          success = false;
        }
        DayFoodCompleteEntity total = dietCompRepo.findByMiSeqAndDfcRegDt(member.getMiSeq(), entity.getDfRegDt());
        total.setDfcTotalCal(totalCal);
        total.setDfcGoal(success);
        dietCompRepo.save(total);
        response = ReStatusAndMessageResponseVO.builder()
        .status(true).message("식단을 삭제했습니다.")
        .build();
      }
    }
    return response;
  }
  
  // 추천 식단 등록
  public ReDietInsertResponseVO addDietSuggest(ReDietSuggestInsertVO data) {
    ReDietInsertResponseVO response = new ReDietInsertResponseVO();
    if (data.getDietContent() == null) {
      response = ReDietInsertResponseVO.builder().status(false).message("추천 식단을 입력하세요.").data(null).
      build();
    }
    else if (data.getDietStatus() == null) {
      response = ReDietInsertResponseVO.builder().status(false).message("추천 식단의 시간대를 입력하세요.")
      .data(null).build();
    }
    else if (data.getDietTotalCal() == null) {
      response = ReDietInsertResponseVO.builder().status(false).message("추천 식단의 칼로리를 입력하세요.")
      .data(null).build();
    }
    else if (data.getDietHard() == null) {
      response = ReDietInsertResponseVO.builder().status(false).message("추천 식단의 다이어트 강도를 입력하세요.")
      .data(null).build();
    }
    else {
      DietSuggestEntity entity = DietSuggestEntity.builder()
          .dietSeq(null)
          .dietContent(data.getDietContent())
          .dietStatus(data.getDietStatus())
          .dietTotalCal(data.getDietTotalCal())
          .dietDate(data.getDietDate())
          .dietHard(data.getDietHard())
          .build();
      suggestRepo.save(entity);
      response = ReDietInsertResponseVO.builder().status(true).message("추천 식단이 등록되었습니다.")
      .data(entity).build();
    }
    return response;
  }

  // 추천 식단 출력
  public ReDietSuggestResponseVO getDietSuggest(String token) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReDietSuggestResponseVO response = new ReDietSuggestResponseVO();
    if (member == null) {
      response = ReDietSuggestResponseVO.builder().data(null).message("로그인 한 회원만 이용가능합니다.").status(false).build();
    } else {
      LocalDate today = LocalDate.now();
      List<DietSuggestEntity> suggestion = suggestRepo.findByDietHardAndDietDate(member.getMiHard(), today);
      if (suggestion.isEmpty()) {
        response = ReDietSuggestResponseVO.builder().data(null).message("식단 추천 목록이 없습니다.").status(false).build();
      } else {
        response = ReDietSuggestResponseVO.builder().data(suggestion).message("식단 추천 목록이 조회되었습니다.").status(true)
            .build();
      }
    }
    return response;
  }
 
  // 추천 주별 식단 출력
  public ReDietSuggestWeeklyFinalVO getDietSuggestWeekly(String token) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReDietSuggestWeeklyFinalVO response = new ReDietSuggestWeeklyFinalVO();
    // 회원이 없다면, 로그인이 안된 것이므로 오류 메시지 출력
    if (member == null) {
      response = ReDietSuggestWeeklyFinalVO.builder().data(null).message("로그인 한 회원만 이용가능합니다.").status(false).build();
    }
    // 로그인이 된 경우,
    else {
      // 오늘 날짜를 가지고 와서
      LocalDate today = LocalDate.now();
      // 회원의 다이어트 강도와 날짜와 일치하는 추천 식단을 일주일치 가져온다.
      List<DietSuggestEntity> suggestion = suggestRepo.findWeeklySuggest(member.getMiHard(), today);
      // 만약에 추천식단이 없다면, 오류메시지 출력
      if (suggestion.isEmpty()) {
        response = ReDietSuggestWeeklyFinalVO.builder().data(null).message("식단 추천 목록이 없습니다.").status(false).build();
      }
      // 추천 식단이 있다면,
      else {
        // 일주일치 식단 추천 목록을 가져와서, 같은 날짜 별로 나눈다.
        List<DietSuggestEntity> list = new ArrayList<>();
        List<ReDietSuggestWeekResponseVO> list2 = new ArrayList<>();
        ReDietSuggestWeekResponseVO entity = new ReDietSuggestWeekResponseVO();
        // 식단 목록의 총 개수만큼 반복문을 돌려서
        for (int i = 0; i < suggestion.size(); i++) {
          // 만약에 첫번째 식단이라면,
          if (i == 0) {
            // 리스트를 새로 정의 하고,
            list = new ArrayList<>();
            // 리스트에 본인의 값 저장
            list.add(suggestion.get(i));
          }
          // 리스트가 마지막 값이 아닌경우,
          // 만약 이전 식단과 날짜가 같다면, 
          else if (suggestion.get(i).getDietDate().isEqual(suggestion.get(i - 1).getDietDate())) {
            // 리스트에 자신의 값 넣고
            list.add(suggestion.get(i));
            // 만약 그 중에서도 마지막 값이라면
            if (i == suggestion.size() - 1) {
              // 날짜와 엔터티에 리스트를 넣고
              String date = suggestion.get(i).getDietDate().getDayOfWeek().toString();
              entity = ReDietSuggestWeekResponseVO.builder().date(date).data(list).build();
              // 리스트에 다시 엔터티를 저장
              list2.add(entity);
            }
          }
          // 만약 이전날과 날짜가 다르다면, 그 요일의 시작 값이므로
          else {
            // 그 이전날의 요일을 구해서엔터티에 저장하고, 
            String date = suggestion.get(i - 1).getDietDate().getDayOfWeek().toString();
            entity = ReDietSuggestWeekResponseVO.builder().date(date).data(list).build();
            // 리스트에 엔터티를 저장한 후
            list2.add(entity);
            // 리스트를 새로 정의한다.
            list = new ArrayList<>();
            // 리스트에 자신의 값 넣고,
            list.add(suggestion.get(i));
            // 만약 그 중에서도 마지막 값이라면
            if (i == suggestion.size() - 1) {
              // 날짜와 엔터티에 리스트를 넣고
              String date2 = suggestion.get(i).getDietDate().getDayOfWeek().toString();
              entity = ReDietSuggestWeekResponseVO.builder().date(date2).data(list).build();
              // 리스트에 다시 엔터티를 저장
              list2.add(entity);
            }
          }
        }
        // 내보내야 하는 값에 데이터를 넣어서 내보냄
        response = ReDietSuggestWeeklyFinalVO.builder().data(list2).message("식단 추천 목록이 조회되었습니다.").status(true).build();
      }
    }
    return response;
  }
  
 // 식단 예시 등록
 public ReDietCalorieInsertResponseVO addDietCalorieEx(ReDietCalorieExInsertVO data, MultipartFile file) {
   ReDietCalorieInsertResponseVO response = new ReDietCalorieInsertResponseVO();
  if (data.getDceContent() == null) {
    response = ReDietCalorieInsertResponseVO.builder().data(null)
        .status(false).message("예시 식단을 입력하세요.").build();
  } else if (file == null) {
    response = ReDietCalorieInsertResponseVO.builder().data(null)
        .status(false).message("예시 식단의 이미지를 입력하세요.").build();
  } else if (data.getDceKcal() == null) {
    response = ReDietCalorieInsertResponseVO.builder().data(null)
        .status(false).message("예시 식단의 칼로리를 입력하세요.").build();
  } 
  else if (calRepo.countByDceContent(data.getDceContent()) != 0) {
    if (data.getDceStandard() != null
        || calRepo.findByDceContent(data.getDceContent()).getDceStandard().equals(data.getDceStandard())) {
      response = ReDietCalorieInsertResponseVO.builder().data(null)
          .status(false).message("이미 존재하는 식단입니다.").build();
    }
    else if (data.getDceStandard() == null || calRepo.findByDceContent(data.getDceContent()).getDceStandard() == null) {
       response = ReDietCalorieInsertResponseVO.builder().data(null)
          .status(false).message("이미 존재하는 식단입니다.").build();
    }
  }else {
     String saveFilePath = "";
     String saveFilePath2 = "";
     try {
       saveFilePath = fileService.saveCalorieImageFile(file);
       saveFilePath2 = fileService.saveImageFile(file);

     } catch (Exception e) {
      response = ReDietCalorieInsertResponseVO.builder().data(null)
        .status(false).message("파일 전송에 실패했습니다.").build();
     }
     DietCalorieExEntity entity = DietCalorieExEntity.builder()
         .dceContent(data.getDceContent())
         .dceImage(saveFilePath)
         .dceKcal(data.getDceKcal())
         .dceStandard(data.getDceStandard())
         .build();
     calRepo.save(entity);
    response = ReDietCalorieInsertResponseVO.builder().data(entity)
        .status(true).message("예시 식단이 등록되었습니다.").build();
   }
   return response;
 }
  
 // 식단 예시 출력
 public ReDietCalorieResponseVO getDietCalorieEx() {
  ReDietCalorieResponseVO response = ReDietCalorieResponseVO.builder()
      .data(calRepo.findAll()).message("식단 예시가 출력되었습니다.").status(true).build();
   return response;
 }

  // 식단 예시를 선택해서 식단 입력
  public ReStatusAndMessageResponseVO addDietByCalorieEx(Long dceSeq, String token) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    DietCalorieExEntity data = calRepo.findByDceSeq(dceSeq);
    ReStatusAndMessageResponseVO response = new ReStatusAndMessageResponseVO();
    if (member == null) {
      response = ReStatusAndMessageResponseVO.builder().status(false).message("로그인 한 회원만 이용가능합니다.").build();
    }
    else if (data == null) {
      response = ReStatusAndMessageResponseVO.builder().status(false).message("존재하지 않는 식단 예시 번호입니다.").build();
     } 
    else{
      DayFoodEntity entity = DayFoodEntity.builder()
      .dfSeq(null)
      .member(member)
      .dfMenu(data.getDceContent())
      .dfImg(data.getDceImage())
      .dfKcal(data.getDceKcal())
      .dfRegDt(LocalDateTime.now())
      .build();
      dietRepo.save(entity);

      List<DayFoodEntity> list = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(), entity.getDfRegDt());
        Integer totalCal = 0;
        for (int i = 0; i < list.size(); i++) {
          totalCal += list.get(i).getDfKcal();
        }
        Boolean success = true;
        if (member.getMiKcal() < totalCal) {
          success = false;
        }
        DayFoodCompleteEntity total = dietCompRepo.findByMiSeqAndDfcRegDt(member.getMiSeq(), entity.getDfRegDt());
        if(total == null){
            total = DayFoodCompleteEntity.builder()
            .dfcDate(entity.getDfRegDt().toLocalDate()).dfcGoal(success).dfcTotalCal(totalCal).member(member).build();
        }
        else{
        total.setDfcTotalCal(totalCal);
        total.setDfcGoal(success);
        }
        dietCompRepo.save(total);
        response = ReStatusAndMessageResponseVO.builder().status(true).message("식단이 등록되었습니다.").build();
      }
    return response;
  }

  // 식단 예시 검색 기능
  public ReDietCalorieResponseVO searchCalorieEx(String keyword) {
    if(keyword == null){
      keyword = "";
    }
    List<DietCalorieExEntity> cal = calRepo.findByDceContentContains(keyword);
    ReDietCalorieResponseVO response = new ReDietCalorieResponseVO();
    if (cal.isEmpty()) {
      response = ReDietCalorieResponseVO.builder()
          .data(null).message("검색어를 포함한 식단 예시가 없습니다.").status(false).build();
    } else {
      response = ReDietCalorieResponseVO.builder()
          .data(cal).message("검색어를 포함한 식단 예시가 출력 되었습니다.").status(true).build();
    }
    return response;
  }

  // 한주 칼로리 섭취량
  public ReDayFoodCompleteVO weeklyCalSum(String token, LocalDate date) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReDayFoodCompleteVO response = new ReDayFoodCompleteVO();
    // 회원이 없다면, 로그인이 안된 것이므로 오류 메시지 출력
    if (member == null) {
      response = ReDayFoodCompleteVO.builder().data(null).message("로그인 한 회원만 이용가능합니다.").status(false).build();
    }
    else {
      ReDayFoodCompleteResponseVO entity = new ReDayFoodCompleteResponseVO();
      List<DayFoodCompleteEntity> list = dietCompRepo.findWeeklyCal(member, date);
      if (list.isEmpty()) {
        response = ReDayFoodCompleteVO.builder().data(null).message("이번주 칼로리 섭취 기록이 존재하지 않습니다.").status(false).build();
      }
      else {
        List<ReDayFoodCompleteResponseVO> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
          entity = ReDayFoodCompleteResponseVO.builder().dfcSeq(list.get(i).getDfcSeq())
              .dfcTotalCal(list.get(i).getDfcTotalCal())
              .date(list.get(i).getDfcDate().getDayOfWeek().toString())
              .dfcDate(list.get(i).getDfcDate()).build();
          data.add(entity);
        }
        response = ReDayFoodCompleteVO.builder().data(data).message("이번주 칼로리 섭취 기록입니다.").status(true).build();
      }
    }
    return response;    
  }
}
