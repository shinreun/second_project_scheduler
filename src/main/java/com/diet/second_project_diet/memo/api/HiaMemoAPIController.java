package com.diet.second_project_diet.memo.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diet.second_project_diet.memo.service.HiaMemoService;
import com.diet.second_project_diet.memo.vo.HiaAddMemoVO;
import com.diet.second_project_diet.memo.vo.HiaMemoDataResponseVO;
import com.diet.second_project_diet.memo.vo.HiaMemoResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "메모정보 관리", description = "메모정보 CRUD 관리")
@RestController
@RequestMapping("/api/memo")
public class HiaMemoAPIController {
    @Autowired HiaMemoService meService;

    @Operation(summary = "메모정보 등록", description = "메모정보 등록합니다.")
    @PutMapping("/add")
    public ResponseEntity<HiaMemoResponseVO> putMemoInfo(@RequestBody HiaAddMemoVO data){
        return new ResponseEntity<>(meService.addMemoInfo(data), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "메모정보 조회", description = "해당 식단의 메모 정보를 조회합니다.")
    @GetMapping("/day")
    public ResponseEntity<HiaMemoDataResponseVO> getMemoInfo(
        @Parameter(description = "오늘의 식단 번호", example = "1") @RequestParam Long dfSeq
    ){
        return new ResponseEntity<HiaMemoDataResponseVO>(meService.getMemoInfo(dfSeq), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "메모정보 수정", description = "오늘의 식단 번호를 검색해 메모 수정합니다.")
    @PatchMapping("/update")
    public ResponseEntity<HiaMemoResponseVO> updateMemoInfo(@RequestBody HiaAddMemoVO data){
        return new ResponseEntity<HiaMemoResponseVO>(meService.updateMemoInfo(data), HttpStatus.ACCEPTED);
    }
}
