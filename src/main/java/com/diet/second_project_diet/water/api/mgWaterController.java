package com.diet.second_project_diet.water.api;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.diet.second_project_diet.repository.WaterInfoRepository;
import com.diet.second_project_diet.water.service.mgWaterService;
import com.diet.second_project_diet.water.vo.mgWaterResponseVO;
import com.diet.second_project_diet.water.vo.mgWeekListWaterVO;
import com.diet.second_project_diet.water.vo.mgWeeklyListVO;
import com.diet.second_project_diet.water.vo.mgDayWaterVO;
import com.diet.second_project_diet.water.vo.mgGoalResponseVO;
import com.diet.second_project_diet.water.vo.mgMonthListVO;

import org.springframework.ui.Model;

@Tag(name = "음수 정보 관리", description = "음수정보 CRUD 관리")
@RestController
@RequestMapping("/api/water")
public class mgWaterController {
    @Autowired mgWaterService wService;


    //수정  증가
    @Operation(summary = "음수량 증가 및 수정", description = "목표 음수량 증가하면 자동으로 수정")
     @PostMapping("/update") 
     public ResponseEntity<mgWaterResponseVO > postUpdateWater(@RequestParam String token, LocalDate wiDate) {
       //Map<String, Object> resultMap = wService.updateWater(wiSeq,wiDate);
        return new ResponseEntity<>(wService.updateWater(token,wiDate),HttpStatus.ACCEPTED);
     }
     // 수정 감소 
     @Operation(summary = "음수량 감소 및 수정", description = "목표 음수량 감소하면 자동으로 수정")
     @PostMapping("/minus") 
     public ResponseEntity<mgWaterResponseVO> postMinusWater(@RequestParam String token, LocalDate wiDate ) {
        // Map<String, Object> resultMap = wService.minusWater(wiSeq,wiDate);
        return new ResponseEntity<>(wService.minusWater(token, wiDate), HttpStatus.ACCEPTED);
        //return new ResponseEntity<> (wService.minusWater(wiSeq, wiDate) ,HttpStatus.ACCEPTED);
     }

         // 그냥 월~일 음수량..(조회)
      @Operation(summary = "하루 음수량 조회 및 달성률 조회", description = "토큰 값을 이용하여 해당일 음수량 조회")
      @GetMapping("/day")
      public ResponseEntity<mgDayWaterVO> getWaterList(
        @Parameter(description = "회원 토큰") @RequestParam String token, @Parameter(description = "조회 일") LocalDate date) {
     
      return new ResponseEntity<>(wService.weekWater(token, date), HttpStatus.ACCEPTED);
      // 리턴을 잘못함. html 방법임. map 으로 변경하는 걸로 하기
      }

      @Operation(summary = "요일별 음수 성공여부", description = "토큰 값을 이용하여 요일별 성공여부")
      @GetMapping("/week")
      public ResponseEntity<mgWeeklyListVO> getWeekWaterList(
        @Parameter(description = "회원 토큰") @RequestParam String token, @Parameter(description = "조회 일") LocalDate date) {
      return new ResponseEntity<>(wService.weekWaterList(token, date), HttpStatus.ACCEPTED);
      
      }
         // 멤버별 입력 -> 달성율&성공여부 출력
         @GetMapping("/goal")
         public ResponseEntity<mgGoalResponseVO> getDdayGoal(@RequestParam String token) throws Exception {
            return new ResponseEntity<>(wService.goalWater(token), HttpStatus.ACCEPTED);
         }
         // 캘린더 물 조회
         @GetMapping("/month")
         public ResponseEntity<mgMonthListVO> getMonthGoal(@RequestParam Long miSeq, Integer year, Integer month ) {
            return new ResponseEntity<>(wService.monthWaterList(miSeq, year, month), HttpStatus.ACCEPTED);
         }
      }

   





