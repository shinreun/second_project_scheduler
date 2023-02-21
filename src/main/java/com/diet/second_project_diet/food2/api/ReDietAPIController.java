package com.diet.second_project_diet.food2.api;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diet.second_project_diet.food2.service.ReFileService;
import com.diet.second_project_diet.food2.service.ReDietService;
import com.diet.second_project_diet.food2.vo.ReDietInsertVO;
import com.diet.second_project_diet.food2.vo.ReDietSuggestInsertVO;
import com.diet.second_project_diet.food2.vo.ReGetDailyDietResponseVO;
import com.diet.second_project_diet.food2.vo.ReStatusAndMessageResponseVO;
import com.diet.second_project_diet.food2.vo.ReDayFoodCompleteVO;
import com.diet.second_project_diet.food2.vo.ReDietCalorieExInsertVO;
import com.diet.second_project_diet.food2.vo.ReDietCalorieInsertResponseVO;
import com.diet.second_project_diet.food2.vo.ReDietInsertResponseVO;
import com.diet.second_project_diet.food2.vo.ReDietSuggestResponseVO;
import com.diet.second_project_diet.food2.vo.ReDietCalorieResponseVO;
import com.diet.second_project_diet.food2.vo.ReDietSuggestWeekResponseVO;
import com.diet.second_project_diet.food2.vo.ReDietSuggestWeeklyFinalVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
@Tag(name = "식단 정보 관리", description = "Daily Diet CRUD")
public class ReDietAPIController {
  @Value("${file.image.dailydiet}")
  String diet_image_path;
  private final ReDietService dService;
  private final ReFileService fileService;

  @Operation(summary = "식단 추가", description = "식단과 사진을 등록합니다.")
  @PutMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReStatusAndMessageResponseVO> putDailyDiet(
      @Parameter(description = "식단 정보") ReDietInsertVO data,
      @Parameter(description = "사진 파일") MultipartFile file,
      @Parameter(description = "회원 토큰", example = "1") String token) {
    return new ResponseEntity<>(dService.addDailyDiet(data, file, token), HttpStatus.OK);
  }

  @Operation(summary = "식단 사진 다운로드", description = "식단의 해당 사진을 다운로드합니다.")
  @GetMapping("/images/{uri}")
  public ResponseEntity<Resource> getImage(
      @Parameter(description = "사진 uri", example = "shinee.jpg") @PathVariable String uri, HttpServletRequest request)
      throws Exception {
    return fileService.getImageFile(uri);
  }

  @Operation(summary = "식단 출력", description = "회원의 해당 일자 식단을 리스트로 출력합니다.")
  @GetMapping("/list")
  public ResponseEntity<ReGetDailyDietResponseVO> getDailyDiet(
      @Parameter(description = "회원 토큰", example = "1") String token,
      @Parameter(description = "날짜", example = "2023-02-10T00:00:00") LocalDateTime date) {
    return new ResponseEntity<>(dService.getDailyDiet(token, date), HttpStatus.OK);
  }

  @Operation(summary = "식단 삭제", description = "회원의 식단 번호에 해당하는 식단 정보를 삭제하고 하루 총 칼로리 양을 업데이트 합니다.")
  @GetMapping("/delete")
  public ResponseEntity<ReStatusAndMessageResponseVO> deleteDailyDiet(
      @Parameter(description = "회원 토큰", example = "1") String token,
      @Parameter(description = "식단 정보 번호", example = "2") Long dfSeq) {
    return new ResponseEntity<>(dService.deleteDailyDiet(token, dfSeq), HttpStatus.OK);
  }

  @Operation(summary = "식단 수정", description = "식단, 사진, 등록시간을 수정합니다.")
  @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReStatusAndMessageResponseVO> updateDailyDiet(
      @Parameter(description = "식단 정보") ReDietInsertVO data,
      @Parameter(description = "사진 파일") MultipartFile file,
      @Parameter(description = "회원 토큰", example = "1") String token,
      @Parameter(description = "식단 정보 번호", example = "2") Long dfSeq,
      @Parameter(description = "식단 등록 일자", example = "2023-02-10T00:00:00") LocalDateTime date) {
    return new ResponseEntity<>(dService.updateDailyDiet(dfSeq, date, file, token, data), HttpStatus.OK);
  }

  @Operation(summary = "추천 식단 추가", description = "추천 식단을 등록합니다.")
  @PutMapping(value = "/suggest/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReDietInsertResponseVO> putSuggestDiet(
      @Parameter(description = "추천 식단 정보") ReDietSuggestInsertVO data) {
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

  
  @Operation(summary = "식단 예시 추가", description = "예시 식단과 사진, 칼로리를 등록합니다.")
  @PutMapping(value = "/calorie/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReDietCalorieInsertResponseVO> putDietCalorieEx(
      @Parameter(description = "예시 식단 정보") ReDietCalorieExInsertVO data,
      @Parameter(description = "예시 사진 파일") MultipartFile file) {
    return new ResponseEntity<>(dService.addDietCalorieEx(data, file), HttpStatus.OK);
  }

  @Operation(summary = "식단 예시 사진 다운로드", description = "칼로리 예시의 해당 사진을 다운로드합니다.")
  @GetMapping("/calorie/image/{uri}")
  public ResponseEntity<Resource> getCalExImage(
      @Parameter(description = "사진 uri", example = "shinee.jpg") @PathVariable String uri, HttpServletRequest request)
      throws Exception {
    return fileService.getCalorieExImageFile(uri);
  }

  @Operation(summary = "식단 예시 출력", description = "칼로리 예시의 목록을 출력합니다.")
  @GetMapping("/calorie/list")
  public ResponseEntity<ReDietCalorieResponseVO> getCalEx() {
    return new ResponseEntity<>(dService.getDietCalorieEx(), HttpStatus.OK);
  }

  @Operation(summary = "식단 예시를 통해 식단 추가", description = "식단 예시의 번호를 통해 식단에 정보를 저장합니다.")
  @GetMapping("/add/bycal") 
  public ResponseEntity<ReStatusAndMessageResponseVO> addDietByCalEx(@Parameter(description = "식단 예시 번호", example = "1") Long dceSeq,
  @Parameter(description = "회원 토큰", example = "1") String token) {
    return new ResponseEntity<>(dService.addDietByCalorieEx(dceSeq, token), HttpStatus.OK);
  }

  @Operation(summary = "식단 예시 검색 기능", description = "키워드를 포함하고 있는 식단예시를 검색합니다.")
  @GetMapping("/search/cal") 
  public ResponseEntity<ReDietCalorieResponseVO> addDietByCalEx(
      @Parameter(description = "검색 키워드", example = "닭") @Nullable String keyword) {
    return new ResponseEntity<>(dService.searchCalorieEx(keyword), HttpStatus.OK);
  }
  
  @Operation(summary = "이번주 칼로리 섭취 기록", description = "해당일 포함하고 있는 일주일의 칼로리 섭취량을 출력합니다.")
  @GetMapping("/week/cal") 
  public ResponseEntity<ReDayFoodCompleteVO> findWeeklyCalSum (@Parameter(description = "날짜", example = "2023-02-02") LocalDate date,
  @Parameter(description = "회원 토큰", example = "1") String token) {
    return new ResponseEntity<>(dService.weeklyCalSum(token, date), HttpStatus.OK);
  }
 
}

