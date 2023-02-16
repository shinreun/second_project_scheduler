package com.diet.second_project_diet.food2.service;
package com.diet.second_project_diet.food2.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.DietSuggestEntity;
import com.diet.second_project_diet.entity.DayFoodCompleteEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.DietCalorieExEntity;
import com.diet.second_project_diet.food2.vo.ReDietInsertVO;
import com.diet.second_project_diet.food2.vo.ReDietSuggestInsertVO;
import com.diet.second_project_diet.food2.vo.ReGetDailyDietResponseVO;
import com.diet.second_project_diet.food2.vo.ReStatusAndMessageResponseVO;
import com.diet.second_project_diet.food2.vo.ReDietCalorieExInsertVO;
import com.diet.second_project_diet.food2.vo.ReDietCalorieInsertResponseVO;
import com.diet.second_project_diet.food2.vo.ReDietCalorieResponseVO;
import com.diet.second_project_diet.food2.vo.ReDietInsertResponseVO;
import com.diet.second_project_diet.food2.vo.ReDietSuggestResponseVO;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.DietSuggestRepository;
import com.diet.second_project_diet.repository.DayFoodCompleteRepository;
import com.diet.second_project_diet.repository.DietCalorieExRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;

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

      List<DayFoodEntity> list = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(), entity.getDfRegDt());
      DayFoodCompleteEntity total = dietCompRepo.findByMiSeqAndDfcRegDt(member.getMiSeq(), entity.getDfRegDt());
      if (total == null) {
        Boolean success = true;
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

  // 식단 출력
  public ReGetDailyDietResponseVO getDailyDiet(String token, LocalDateTime date) {
    MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
    ReGetDailyDietResponseVO response = new ReGetDailyDietResponseVO();
    if (member == null) {
      
    } else {
      List<DayFoodEntity> diet = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(), date);
      if (diet == null) {
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
        LocalDateTime today = LocalDateTime.now();
        DayFoodEntity newEntity = new DayFoodEntity(entity.getDfSeq(), member, data.getMenu(), saveFilePath, today,
            data.getKcal());
        dietRepo.save(newEntity);
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
        DayFoodEntity newEntity = new DayFoodEntity(entity.getDfSeq(), member, data.getMenu(), saveFilePath, date,
            data.getKcal());
        dietRepo.save(newEntity);
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
      System.out.println(today);
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
   } else {
     String saveFilePath = "";
     try {
       saveFilePath = fileService.saveCalorieImageFile(file);
     } catch (Exception e) {
      response = ReDietCalorieInsertResponseVO.builder().data(null)
        .status(false).message("파일 전송에 실패했습니다.").build();
     }
     DietCalorieExEntity entity = DietCalorieExEntity.builder()
         .dceContent(data.getDceContent())
         .dceImage(saveFilePath)
         .dceKcal(data.getDceKcal())
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
        total.setDfcTotalCal(totalCal);
        total.setDfcGoal(success);
        dietCompRepo.save(total);
        response = ReStatusAndMessageResponseVO.builder().status(true).message("식단이 등록되었습니다.").build();
      }
    return response;
  }
}
