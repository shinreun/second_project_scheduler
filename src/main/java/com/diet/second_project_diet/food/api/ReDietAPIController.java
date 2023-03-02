package com.diet.second_project_diet.food.api;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diet.second_project_diet.food.service.HiaDietFoodService;
import com.diet.second_project_diet.food.service.ReDietService;
import com.diet.second_project_diet.food.service.ReFileService;
import com.diet.second_project_diet.food.vo.ReDayFoodCompleteVO;
import com.diet.second_project_diet.food.vo.ReDietCalorieResponseVO;
import com.diet.second_project_diet.food.vo.ReDietInsertVO;
import com.diet.second_project_diet.food.vo.ReGetDailyDietResponseVO;
import com.diet.second_project_diet.food.vo.ReStatusAndMessageResponseVO;
import com.diet.second_project_diet.food.vo.HiaDetailResponseVO;

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
  private final HiaDietFoodService dfService;
  private final ReDietService dService;
  private final ReFileService fileService;

  @Operation(summary = "식단 추가", description = "식단과 사진 및 메모를 등록합니다.")
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

  @Operation(summary = "하루치 식단 출력", description = "회원의 해당 일자 식단을 리스트로 출력합니다.")
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

  @Operation(summary = "식단 수정", description = "식단, 사진, 등록시간 및 메모를 수정합니다.")
  @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReStatusAndMessageResponseVO> updateDailyDiet(
      @Parameter(description = "식단 정보") ReDietInsertVO data,
      @Parameter(description = "사진 파일") MultipartFile file,
      @Parameter(description = "회원 토큰", example = "1") String token,
      @Parameter(description = "식단 정보 번호", example = "2") Long dfSeq,
      @Parameter(description = "식단 등록 일자", example = "2023-02-10T00:00:00") LocalDateTime date) {
    return new ResponseEntity<>(dService.updateDailyDiet(dfSeq, date, file, token, data), HttpStatus.OK);
  }
  
  @Operation(summary = "식단 예시를 선택해 식단 추가", description = "식단 예시의 seq 번호를 통해 오늘 먹은 식단을 등록합니다.")
  @GetMapping("/add/bycal") 
  public ResponseEntity<ReStatusAndMessageResponseVO> addDietByCalEx(@Parameter(description = "식단 예시 번호", example = "1") Long dceSeq,
  @Parameter(description = "회원 토큰", example = "1") String token) {
    return new ResponseEntity<>(dService.addDietByCalorieEx(dceSeq, token), HttpStatus.OK);
  }
  
  
  @Operation(summary = "식단 상세보기", description = "식단 seq번호를 통해 상세내용과 메모정보 조회")
    @GetMapping("/day/detail")
    public ResponseEntity<HiaDetailResponseVO> getDetailDiet(
        @Parameter(description = "오늘의 식단 번호", example = "1") Long dfSeq){
            return new ResponseEntity<HiaDetailResponseVO>(dfService.getDetailDiet(dfSeq), HttpStatus.ACCEPTED);
    }
 
}

