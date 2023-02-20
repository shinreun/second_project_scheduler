package com.diet.second_project_diet.pill.api;

import io.micrometer.common.lang.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diet.second_project_diet.pill.service.DhPillInfoService;
import com.diet.second_project_diet.pill.vo.DhListResponseVO;
import com.diet.second_project_diet.pill.vo.DhListResponseVO2;
import com.diet.second_project_diet.pill.vo.DhPillInfoInsertVO;
import com.diet.second_project_diet.pill.vo.DhRePillInfoInsertVO;
import com.diet.second_project_diet.pill.vo.DhResponseVO;

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

    // 약 추가 완료 -service 조건문 추가하기
    @Operation(summary = "약 추가", description = "약을 등록합니다.")
    @PutMapping("/add")
    public ResponseEntity<DhResponseVO> putAddPillInfo(
            @Parameter(description = "약 정보") @RequestBody DhPillInfoInsertVO data,
            @Parameter(description = "회원 토큰", example = "1") @RequestParam String token) {
        return new ResponseEntity<>(piService.addPillInfo(data, token), HttpStatus.OK);
    }

    // 수정 - 일단 수정은 되고 조건문 추가하기??
    @Operation(summary = "약 수정", description = "약을 수정합니다.")
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
            @Parameter(description = "약 번호", example = "1") @RequestParam Long piSeq,
            @Parameter(description = "약 이름", example = "빈혈") @RequestParam String piName
    // @Parameter(description = "약 정보") @RequestBody DhPillInfoInsertVO data,
    ) {
        return new ResponseEntity<>(piService.deletePillInfo(token, piSeq, piName), HttpStatus.OK);
    }

    // 조회 - 일단 성공

    @Operation(summary = "약 조회 ", description = "회원이 현재 섭취중인 약을 조회합니다.")
    @GetMapping("/info")
    public ResponseEntity<DhListResponseVO> getPillInfo(
            @Parameter(description = "회원 번호", example = "1") @RequestParam Long miSeq
    ) {
        return new ResponseEntity<>(piService.getPillInfo(miSeq), HttpStatus.OK);
    }

    @Operation(summary = "약 성공 여부 조회", description = "섭취에 성공한 약을 조회합니다.")
    @GetMapping("/info/success")
    public ResponseEntity<DhResponseVO> getSuccessPillInfo(
            @Parameter(description = "회원 번호", example = "1") @RequestParam Long miSeq,
            @Parameter(description = "날짜", example = "2023-02-09") @RequestParam LocalDate picDate
            ) {
        return new ResponseEntity<>(piService.getPillInfo2(picDate, miSeq), HttpStatus.OK);
    }
















    // @PostMapping("/update")
    // public ResponseEntity<DhResponseVO > postUpdateWater(@RequestParam String
    // token, LocalDate wiDate) {
    // //Map<String, Object> resultMap = wService.updateWater(wiSeq,wiDate);
    // return new
    // ResponseEntity<>(wService.updateWater(token,wiDate),HttpStatus.ACCEPTED);
    // }

}