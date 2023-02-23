package com.diet.second_project_diet.food.api;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diet.second_project_diet.food.service.HiaDietFoodService;
import com.diet.second_project_diet.food.service.ReDietService;
import com.diet.second_project_diet.food.vo.HiaDataResponseVO;
import com.diet.second_project_diet.food.vo.HiaDayTotalCalVO;
import com.diet.second_project_diet.food.vo.HiaDdayGoalResponseVO;

import com.diet.second_project_diet.food.vo.HiaGoalResponseVO;
import com.diet.second_project_diet.food.vo.HiaProgGoalResponseVO;
import com.diet.second_project_diet.food.vo.HiaResponseVO;
import com.diet.second_project_diet.food.vo.ReDayFoodCompleteVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "칼로리 관련 정보 관리", description = "칼로리 및 달성률 관련 정보 관리 CRUD API")
@RestController
@RequestMapping("/api/cal")
public class HiaDietFoodAPIController {
    @Autowired HiaDietFoodService dfService;
    @Autowired ReDietService dService;

    @Operation(summary = "해당일 섭취 칼로리 합계 데이터 입력", description = "해당일 섭취 칼로리를 계산 후 데이터 베이스에 저장 합니다 / 등록 할 회원 토큰 및 날짜를 입력하세요")
    @PutMapping("/sum")
    public ResponseEntity<HiaResponseVO> putDietFood(
        @Parameter(description = "회원토큰 및 해당일") @RequestBody HiaDayTotalCalVO data,
        @Parameter(description = "회원 토큰 값") @RequestParam String token){
        return new ResponseEntity<>(dfService.sumTotalCal(data, token), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "년/월 목표달성 여부", description = "해당년/월에 목표 달성 여부를 출력합니다 / 회원 토큰, 년, 월을 입력하세요.")
    @GetMapping("/month") 
    public ResponseEntity<HiaGoalResponseVO> getMonthDietFood(
        @Parameter(description = "회원 토큰", example = "1") @RequestParam String token, 
        @Parameter(description = "조회 연도", example ="2023") @RequestParam Integer year, 
        @Parameter(description = "조회 월", example = "2") @RequestParam Integer month){
        return new ResponseEntity<>(dfService.getMonthDietFood(token, year, month), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "해당일 섭취 칼로리 조회", description = "해당일에 섭취한 총 칼로리를 조회합니다.")
    @GetMapping("/day")
    public ResponseEntity<HiaDataResponseVO> getDayKcal(
        @Parameter(description = "회원 토큰", example = "1") @RequestParam String token, 
        @Parameter(description = "조회 년. 월. 일", example = "2023-02-09") @RequestParam LocalDate today){
        return new ResponseEntity<>(dfService.getDayKcal(token, today), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "해당 월 목표 달성률 조회", description = "달성률을 월단위로 조회합니다.")
    @GetMapping("/goal")
    public ResponseEntity<HiaProgGoalResponseVO> getProgGoal(
        @Parameter(description = "회원 토큰", example = "1") @RequestParam String token, 
        @Parameter(description = "조회 월", example = "2") @RequestParam Integer month){
        return new ResponseEntity<HiaProgGoalResponseVO>(dfService.getProgGoal(token, month), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "D-day 기준 목표 달성률 조회", description = "달성률을 D-day를 기준으로 조회합니다.")
    @GetMapping("/day/goal")
    public ResponseEntity<HiaDdayGoalResponseVO> getDdayGoal(
            @Parameter(description = "회원 토큰", example = "1") @RequestParam String token) throws Exception {
        return new ResponseEntity<HiaDdayGoalResponseVO>(dfService.getDdayGoal(token), HttpStatus.ACCEPTED);
    }
    
    @Operation(summary = "이번주 칼로리 섭취 기록", description = "해당일 포함하고 있는 일주일의 칼로리 섭취량을 출력합니다.")
    @GetMapping("/week") 
  public ResponseEntity<ReDayFoodCompleteVO> findWeeklyCalSum (@Parameter(description = "날짜", example = "2023-02-02") LocalDate date,
      @Parameter(description = "회원 토큰", example = "1") String token) {
    return new ResponseEntity<>(dService.weeklyCalSum(token, date), HttpStatus.OK);
  }

}










