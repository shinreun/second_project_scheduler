// package com.diet.second_project_diet.food2.service;

// import org.springframework.stereotype.Service;

// @Service
// public class ReAllService {
//   // 식단, 음수량, 약, 추천 식단....
//   public ReGetDailyDietResponseVO getDailyDiet(String token, LocalDateTime date) {
//     MemberInfoEntity member = memberRepo.findByMiTokenIs(token);
//     ReGetDailyDietResponseVO response = new ReGetDailyDietResponseVO();
//     if (member == null) {
      
//     } else {
//       List<DayFoodEntity> diet = dietRepo.findByMiSeqAndDfRegDt(member.getMiSeq(), date);
//       if (diet.isEmpty()) {
//         response = ReGetDailyDietResponseVO.builder().status(false).message("등록된 식단이 없습니다.")
//             .list(null).build();
//       } else {
//         response = ReGetDailyDietResponseVO.builder().status(true).message(date + " 의 식단입니다.")
//             .list(diet).build();
//       }
//     }
//     return response;
//   }

// }
