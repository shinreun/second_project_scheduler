package com.diet.second_project_diet.pill.api;


import io.micrometer.common.lang.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.diet.second_project_diet.pill.service.DhPillInfoService;
import com.diet.second_project_diet.pill.vo.DhListResponseVO;
import com.diet.second_project_diet.pill.vo.DhListResponseVO2;
import com.diet.second_project_diet.pill.vo.DhPillInfoInsertVO;
import com.diet.second_project_diet.pill.vo.DhRePillInfoInsertVO;
import com.diet.second_project_diet.pill.vo.DhResponseVO;
import com.diet.second_project_diet.pill.vo.DhMonthlyVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Tag(name = "약 정보 관리", description = "약 정보 CRUD")
@RestController
@RequestMapping("/api/pill")
@RequiredArgsConstructor
public class DhPillAPIController {
   private final DhPillInfoService piService;

   // 약 추가 완료
   @Operation(summary = "약 추가", description = "약을 등록합니다.")
   @PutMapping("/add")
   public ResponseEntity<DhResponseVO> putAddPillInfo(
           @Parameter(description = "약 정보") @RequestBody DhPillInfoInsertVO data,
           @Parameter(description = "회원 토큰", example = "1") @RequestParam String token
   ) {
      return new ResponseEntity<>(piService.addPillInfo(data, token), HttpStatus.OK);
   }

   // 수정 - 일단 수정은 되고 조건문 추가하기??
   @Operation(summary = "약 수정", description = "약 이름과 총 섭취 수량을 수정합니다.")
   @PatchMapping("/update")
   public ResponseEntity<DhResponseVO> updatePillInfo(
           @Parameter(description = "회원 토큰", example = "1") @RequestParam String token,
           @Parameter(description = "약 정보") @RequestBody DhRePillInfoInsertVO data,
           @Parameter(description = "약 번호", example = "1") @RequestParam Long piSeq) {
      return new ResponseEntity<>(piService.updatePillInfo(token, data, piSeq), HttpStatus.OK);
   }

   // 삭제
   @Operation(summary = "약 삭제", description = "약을 삭제합니다.")
   @DeleteMapping("/delete")
   public ResponseEntity<DhResponseVO> deletePillInfo(
           @Parameter(description = "회원 토큰", example = "1") @RequestParam String token,
           @Parameter(description = "약 번호", example = "1") @RequestParam Long piSeq
   ) {
      return new ResponseEntity<>(piService.deletePillInfo(token, piSeq), HttpStatus.OK);
   }

   // 약 조회
   @Operation(summary = "약 조회 ", description = "회원이 현재 섭취중인 약을 조회합니다.")
   @GetMapping("/info")
   public ResponseEntity<DhListResponseVO> getPillInfo(
           @Parameter(description = "회원 번호", example = "1") @RequestParam String token
   ) {
      return new ResponseEntity<>(piService.getPillInfo(token), HttpStatus.OK);
   }

   // 약 성공 여부 조회
   @Operation(summary = "약 성공 여부 조회", description = "회원이 섭취하는 모든 약을 다 먹은 경우, 성공으로 출력합니다.")
   @GetMapping("/info/success")
   public ResponseEntity<DhResponseVO> getSuccessPillInfo(
           @Parameter(description = "회원 번호", example = "1") @RequestParam String token,
           @Parameter(description = "날짜", example = "2023-02-09") @RequestParam LocalDate picDate
   ) {
      return new ResponseEntity<>(piService.getPillInfo2(picDate, token), HttpStatus.OK);
   }

   // 작업중
   // 수정 > 증가
   @Operation(summary = "약 섭취량 증가", description = "섭취량 증가 및 섭취 성공 여부 자동으로 수정")
   @PostMapping("/update/plus")
   public ResponseEntity<DhResponseVO> postPlusPill(
           // @RequestParam String token, LocalDate picDate
           @Parameter(description = "회원 번호", example = "1") @RequestParam String token,
           @Parameter(description = "약 번호", example = "1") @RequestParam Long seq
   ) {
      // Map<String, Object> resultMap = wService.updateWater(wiSeq,wiDate);
      return new ResponseEntity<>(piService.updatePlusPill(token, seq), HttpStatus.OK);
   }


   // 작업완료 - test 안해봄
   // 수정 > 감소
   @Operation(summary = "약 섭취량 감소", description = "섭취량 감소 및 섭취 성공 여부 자동으로 수정")
   @PostMapping("/update/minus")
   public ResponseEntity<DhResponseVO> postMinusPill(
           // @RequestParam String token, LocalDate picDate
           @Parameter(description = "회원 번호", example = "1") @RequestParam String token,
           @Parameter(description = "약 번호", example = "1") @RequestParam Long seq
           // @Parameter(description = "날짜", example = "2023-02-09") @RequestParam LocalDate date
   ) {
      // Map<String, Object> resultMap = wService.updateWater(wiSeq,wiDate);
      return new ResponseEntity<>(piService.updateMinusPill(token, seq), HttpStatus.OK);
   }

   // 작업중 - 한달단위로 조회
   @Operation(summary = "약 한달단위로 조회", description = "회원이 현재 섭취중인 약을 한달 단위로 조회합니다.")
   @GetMapping("/info/month")
   public ResponseEntity<DhMonthlyVO> findMonthPillInfo(
           @Parameter(description = "회원 번호", example = "1") @RequestParam String token,
           @Parameter(description = "날짜", example = "2023-02-09") @RequestParam LocalDate date
   ) {
      return new ResponseEntity<>(piService.pillMonthList(token, date), HttpStatus.OK);
   }



}






































