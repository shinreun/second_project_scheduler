package com.diet.second_project_diet.weight.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diet.second_project_diet.weight.sevice.WeightService;
import com.diet.second_project_diet.weight.vo.ReGetWeightResponseVO;
import com.diet.second_project_diet.weight.vo.ReStatusAndMessageResponseVO;
import com.diet.second_project_diet.weight.vo.ReWeightDifferneceResponseVO;
import com.diet.second_project_diet.weight.vo.ReGetWeightResponseByTermVO;
import com.diet.second_project_diet.weight.vo.ReWeightCompareVO;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/weight")
@RequiredArgsConstructor
@Tag(name = "체중 정보 관리", description = "Daily weight CRUD")
public class WeightAPIController {
  private final WeightService weightService;

  @Operation(summary = "몸무게 추가 및 수정", description = "오늘의 몸무게를 등록/ 이미 몸무게가 등록되어있다면 수정합니다.")
  @PutMapping("/add")
  public ResponseEntity<ReGetWeightResponseVO> putWeight(
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token,
      @Parameter(description = "몸무게", example = "60") @RequestParam Double weight
  ) {
    return new ResponseEntity<>(weightService.addDailyWeight(weight, token), HttpStatus.OK);
  }
  
  @Operation(summary = "몸무게 수정", description = "몸무게 seq번호를 통해 몸무게를 수정합니다.")
  @GetMapping("/update")
  public ResponseEntity<ReGetWeightResponseVO>  updateWeight(
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token,
      @Parameter(description = "조회하고 싶은 몸무게 seq 번호", example = "1") @RequestParam Long weiSeq, 
      @Parameter(description = "수정하고 싶은 몸무게", example = "55") @RequestParam Double weight
  ) {
    return new ResponseEntity<>(weightService.updateDailyWeight(token, weiSeq, weight), HttpStatus.OK);
  }

  @Operation(summary = "몸무게 조회", description = "해당일자의 몸무게를 조회합니다.")
  @GetMapping("/list/day")
  public ResponseEntity<ReGetWeightResponseVO>  getWeight(
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token,
      @Parameter(description = "조회하고 싶은 날짜", example = "2023-02-15") @RequestParam LocalDate date
    ) {
    return new ResponseEntity<>(weightService.getDailyWeight(token, date), HttpStatus.OK);
  }
  
  @Operation(summary = "몸무게 삭제", description = "몸무게 seq번호를 통해 몸무게를 삭제합니다.")
  @GetMapping("/delete")
  public ResponseEntity<ReStatusAndMessageResponseVO>  deleteWeight (
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token,
      @Parameter(description = "삭제하고 싶은 몸무게 seq 번호", example = "1") @RequestParam Long weiSeq
  ) {
    return new ResponseEntity<>(weightService.deleteDailyWeight(token, weiSeq), HttpStatus.OK);
  }
  
  @Operation(summary = "일주일 간 몸무게 조회", description = "날짜를 통해 일주일간의 몸무게를 조회합니다.")
  @GetMapping("/list/week")
  public ResponseEntity<ReGetWeightResponseByTermVO> getWeeklyWieght(
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token,
      @Parameter(description = "기준 날짜 (해당일을 포함한 일주일)", example = "2023-02-15") @RequestParam LocalDate date
  ) {
    return new ResponseEntity<>(weightService.getWeeklyWeight(token, date), HttpStatus.OK);
  }

  @Operation(summary = "한달 간 몸무게 조회", description = "날짜를 통해 한달간의 몸무게를 조회합니다.")
  @GetMapping("/list/month")
  public ResponseEntity<ReGetWeightResponseByTermVO> getMonthlyWeight(
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token,
      @Parameter(description = "기준 날짜 (해당일을 포함한 한달)", example = "2023-02-15") @RequestParam LocalDate date
  ) {
    return new ResponseEntity<>(weightService.getMonthlyWeight(token, date), HttpStatus.OK);
  }
  
  @Operation(summary = "몸무게와 변화량 조회", description = "회원의 모든 몸무게 데이터와 그 변화량을 조회합니다.")
  @GetMapping("/list/diff")
  public ResponseEntity<ReWeightDifferneceResponseVO> getAllWeightAndDiff(
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token) {
    return new ResponseEntity<>(weightService.getWeightDifference(token), HttpStatus.OK);
  }
  
  @Operation(summary = "목표무게, 현재무게, 변화량 출력", description = "회원의 목표 무게와 가장 최근에 등록된 무게, 그 직전 무게와의 변화량을 출력합니다.")
  @GetMapping("/compare")
  public ResponseEntity<ReWeightCompareVO> getGoalDiffWeight(
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token) {
    return new ResponseEntity<>(weightService.getWeightCompare(token), HttpStatus.OK);
  }

}
