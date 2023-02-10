package com.diet.second_project_diet.food2.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.food2.vo.ReDietInsertVO;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ReDietService {
  @Value("${file.image.dailydiet}") String diet_image_path;
  private final MemberInfoRepository memberRepo;
  private final DayFoodRepository dietRepo;
  private final FileService fileService;

  // 식단 출력
  public Map<String, Object> getDailyDiet(String token, LocalDateTime date) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    MemberInfoEntity member = memberRepo.findByMiTokenContains(token);
    if (member == null) {
      map.put("status", false);
      map.put("message", "로그인 한 회원만 이용가능합니다.");
    }
    else {
      // @DateTimeFormat(pattern = "yyyy-MM-dd")
      // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      // LocalDateTime day = ;
      List<DayFoodEntity> diet = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(),date);

      map.put("diet", diet);
      map.put("status", true);
      map.put("message","식단입니다.");
    }
    return map;
  }
  
  // 식단 추가
  public Map<String, Object> addDailyDiet(ReDietInsertVO data, MultipartFile file, String token) 
   {
     Map<String, Object> map = new LinkedHashMap<String, Object>();
     MemberInfoEntity member = memberRepo.findByMiTokenContains(token);
     if (member == null) {
       map.put("status", false);
       map.put("message", "로그인 한 회원만 이용가능합니다.");
     }
     else {
       String saveFilePath = "";
       try {
         saveFilePath = fileService.saveImageFile(file);
       } catch (Exception e) {
         System.out.println("파일 전송 실패");
         map.put("status", false);
         map.put("message", "파일 전송에 실패했습니다.");
         map.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
       }
       LocalDateTime today = LocalDateTime.now();
       DayFoodEntity entity = new DayFoodEntity(null, member, data.getMenu(), saveFilePath, today , data.getKcal());
       dietRepo.save(entity);

       map.put("status", true);
       map.put("message", "식단이 등록되었습니다.");
     }
    return map;
  }
}
