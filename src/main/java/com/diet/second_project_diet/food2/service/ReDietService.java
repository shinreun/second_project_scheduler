// package com.diet.second_project_diet.food2.service;

// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.LinkedHashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import com.diet.second_project_diet.entity.DayFoodEntity;
// import com.diet.second_project_diet.entity.MemberInfoEntity;
// import com.diet.second_project_diet.food2.vo.ReDietInsertVO;
// import com.diet.second_project_diet.repository.DayFoodRepository;
// import com.diet.second_project_diet.repository.MemberInfoRepository;

// import lombok.RequiredArgsConstructor;


// @Service
// @RequiredArgsConstructor
// public class ReDietService {
//   @Value("${file.image.dailydiet}") String diet_image_path;
//   private final MemberInfoRepository memberRepo;
//   private final DayFoodRepository dietRepo;
//   private final FileService fileService;

//   public Map<String, Object> getDailyDiet(String token) {
//     Map<String, Object> map = new LinkedHashMap<String, Object>();
//     MemberInfoEntity member = memberRepo.findByMiTokenContains(token);
//     if (member == null) {
//       map.put("status", false);
//       map.put("message", "로그인 한 회원만 이용가능합니다.");
//     }
//     else {
//       Date today = new Date();
//       dayfood
//     }



//     return map;
//   }
  
//   // 식단 추가
//   public Map<String, Object> addDailyDiet(ReDietInsertVO data, MultipartFile file, String token) 
//    {
//      Map<String, Object> map = new LinkedHashMap<String, Object>();
//      MemberInfoEntity member = memberRepo.findByMiTokenContains(token);
//     String saveFilePath = "";
//     try {
//       saveFilePath = fileService.saveImageFile(file);
//     } catch (Exception e) {
//       System.out.println("파일 전송 실패");
//       map.put("status", false);
//       map.put("message", "파일 전송에 실패했습니다.");
//       map.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
//     }
//     DayFoodEntity entity = new DayFoodEntity(null, member, data.getMenu(), saveFilePath , new Date(), data.getKcal());
//     dietRepo.save(entity);
    
//     map.put("status", true);
//     map.put("message", "식단이 등록되었습니다.");
//     return map;
//   }
// }
