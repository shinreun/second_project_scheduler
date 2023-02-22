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
import com.diet.second_project_diet.water.vo.mgUpDownResponseVO;
import com.diet.second_project_diet.water.vo.mgWeekListVO;
import com.diet.second_project_diet.water.vo.mgWeekResponseVO;
import com.diet.second_project_diet.water.vo.mgDayWaterVO;
import com.diet.second_project_diet.water.vo.mgDdayResponseVO;
import com.diet.second_project_diet.water.vo.mgMonthListVO;

import org.springframework.ui.Model;

@Tag(name = "음수 정보 관리", description = "음수정보 CRUD 관리")
@RestController
@RequestMapping("/api/water")
public class mgWaterController {
    @Autowired mgWaterService wService;


    //하루 섭취량 수정 - 증가
      @Operation(summary = "음수량 증가 및 수정", description = "목표 음수량 증가하면 자동으로 수정")
      @PostMapping("/increase") 
      public ResponseEntity<mgUpDownResponseVO > postIncreaseWater(@RequestParam String token, LocalDate wiDate) {
        return new ResponseEntity<>(wService.increaseWater(token,wiDate),HttpStatus.ACCEPTED);
     }
     // 하루 섭취량 수정 - 감소 
     @Operation(summary = "음수량 감소 및 수정", description = "목표 음수량 감소하면 자동으로 수정")
     @PostMapping("/decrease") 
     public ResponseEntity<mgUpDownResponseVO> postDecreaseWater(@RequestParam String token, LocalDate wiDate ) {
        return new ResponseEntity<>(wService.decreaseWater(token, wiDate), HttpStatus.ACCEPTED);
     }

     // 요일별 달성여부 조회(주 단위)
      @Operation(summary = "요일별(주단위) 음수 성공여부", description = "토큰 값을 이용하여 요일별(주단위) 성공여부")
      @GetMapping("/week")
      public ResponseEntity<mgWeekResponseVO> getWeekWaterList(
      @Parameter(description = "회원 토큰") @RequestParam String token, @Parameter(description = "조회 일") LocalDate date) {
         return new ResponseEntity<>(wService.weeklist(token, date), HttpStatus.ACCEPTED);
   
   }
        // 하루 목표 음수량 조회 + 하루 달성율(%) 조회
      @Operation(summary = "하루 음수량 조회 및 달성률 조회", description = "토큰 값을 이용하여 해당일 음수량 조회")
      @GetMapping("/day")
      public ResponseEntity<mgDayWaterVO> getWaterList(
        @Parameter(description = "회원 토큰") @RequestParam String token, @Parameter(description = "조회 일") LocalDate date) {
      return new ResponseEntity<>(wService.daylist(token, date), HttpStatus.ACCEPTED);
      }

         // d-day기준목표달성율과 성공여부
      @Operation(summary = "D-DAY 기준 목표달성율과 성공여부", description = "회원번호를 이용하여 D-DAY 목표달성율 조회")
      @GetMapping("/dday")
      public ResponseEntity<mgDdayResponseVO> getDdayList(@RequestParam String token) throws Exception {
         return new ResponseEntity<>(wService.ddaylist(token), HttpStatus.ACCEPTED);
         }


         // 캘린더 물 조회
      @Operation(summary = "한달단위 음수 성공여부", description = "토큰 값을 이용하여 한달 성공여부 조회")
      @GetMapping("/month")
      public ResponseEntity<mgMonthListVO> getMonthList(@RequestParam String token, Integer year, Integer month ) {
         return new ResponseEntity<>(wService.monthlist(token, year, month), HttpStatus.ACCEPTED);
         }
      }

   





