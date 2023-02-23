package com.diet.second_project_diet.food.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diet.second_project_diet.food.service.ReDietService;
import com.diet.second_project_diet.food.vo.ReDietInsertResponseVO;
import com.diet.second_project_diet.food.vo.ReDietSuggestInsertVO;
import com.diet.second_project_diet.food.vo.ReDietSuggestResponseVO;
import com.diet.second_project_diet.food.vo.ReDietSuggestWeeklyFinalVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/suggest")
@RequiredArgsConstructor
@Tag(name = "추천 식단 정보 관리", description = "Diet Suggest List CRUD")
public class ReSuggestAPIController {
  private final ReDietService dService;

  @Operation(summary = "추천 식단 추가", description = "추천 식단을 등록합니다.")
  @PutMapping("/suggest/add")
  public ResponseEntity<ReDietInsertResponseVO> putSuggestDiet(
      @Parameter(description = "추천 식단 정보") @RequestBody ReDietSuggestInsertVO data) {
    return new ResponseEntity<>(dService.addDietSuggest(data), HttpStatus.OK);
  }

  @Operation(summary = "당일 추천 식단 출력", description = "회원의 다이어트 강도에 일치하는 하루치 추천 식단이 출력됩니다.")
  @GetMapping("/suggest/list")
  public ResponseEntity<ReDietSuggestResponseVO> getSuggestDiet(
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token) {
    return new ResponseEntity<>(dService.getDietSuggest(token), HttpStatus.OK);
  }
  
  @Operation(summary = "해당 주 추천 식단 출력", description = "회원의 다이어트 강도에 일치하는 일주일치 추천 식단이 출력됩니다.")
  @GetMapping("/suggest/week")
  public ResponseEntity<ReDietSuggestWeeklyFinalVO> getSuggestWeeklyDiet(
      @Parameter(description = "회원 토큰", example = "1") @RequestParam String token) {
    return new ResponseEntity<>(dService.getDietSuggestWeekly(token), HttpStatus.OK);
  }
 
}

