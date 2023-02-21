package com.diet.second_project_diet.member.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diet.second_project_diet.member.service.HiaMemberService;
import com.diet.second_project_diet.member.vo.HiaAddMemberInfoVO;
import com.diet.second_project_diet.member.vo.HiaDataResponseVO;
import com.diet.second_project_diet.member.vo.HiaResponseVO;
import com.diet.second_project_diet.member.vo.HiaTimeResponseVO;
import com.diet.second_project_diet.member.vo.HiaUpdateMemberInfoVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "회원정보 관리", description = "회원정보 CRUD 관리")
@RestController
@RequestMapping("/api/member")
public class HiaMemberAPIController {
    @Autowired HiaMemberService mService;

    @Operation(summary = "회원정보 조회", description = "마이페이지에서 회원정보 조회기능")
    @GetMapping("/info")
    public ResponseEntity<HiaDataResponseVO> getMemberInfo(
        @Parameter(description = "회원번호", example="1") @RequestParam Long seq){
        return new ResponseEntity<>(mService.getMemberInfo(seq), HttpStatus.ACCEPTED);
    }    

    @Operation(summary = "회원정보 등록", description = "회원정보 등록(회원가입)")
    @PutMapping("/add")
    public ResponseEntity<HiaResponseVO> putMemberAdd(@RequestBody HiaAddMemberInfoVO data){
        return new ResponseEntity<>(mService.addMemberInfo(data), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표 칼로리 변경", description = "회원별 목표 칼로리 변경")
    @PatchMapping("/update/kcal")
    public ResponseEntity<HiaResponseVO> patchGoalKcal(@RequestBody HiaUpdateMemberInfoVO data){
        return new ResponseEntity<>(mService.updateGoalKcal(data), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표  몸무게 변경", description = "회원별 목표 몸무게 변경")
    @PatchMapping("/update/kg")
    public ResponseEntity<HiaResponseVO> patchGoalKg(@RequestBody HiaUpdateMemberInfoVO data){
        return new ResponseEntity<>(mService.updateGoalKg(data), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표 음수량 변경", description = "회원별 목표 음수량 변경")
    @PatchMapping("/update/water")
    public ResponseEntity<HiaResponseVO> patchGoalWater(@RequestBody HiaUpdateMemberInfoVO data){
        return new ResponseEntity<>(mService.updateGoalWater(data), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표 날짜 변경", description = "회원별 목표 날짜 변경")
    @PatchMapping("/update/day")
    public ResponseEntity<HiaResponseVO> patchGoalDay(@RequestBody HiaUpdateMemberInfoVO data){
        return new ResponseEntity<>(mService.updateGoalDay(data), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표 다이어트 강도 변경", description = "회원별 목표 다이어트 강도 변경")
    @PatchMapping("/update/hard")
    public ResponseEntity<HiaResponseVO> patchHard(@RequestBody HiaUpdateMemberInfoVO data){
        return new ResponseEntity<>(mService.updateHard(data), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "회원 탈퇴", description = "회원탈퇴 시 상태 값 변경")
    @PatchMapping("/delete")
    public ResponseEntity<HiaResponseVO> patchMemberStatus(@RequestBody HiaUpdateMemberInfoVO data){
        return new ResponseEntity<>(mService.deleteMember(data), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "D-Day 출력", description = "회원번호 입력 하면 D-Day 계산")
    @GetMapping("/dDay")
    public ResponseEntity<HiaTimeResponseVO> getDday(
        @Parameter(description = "회원번호", example = "1") @RequestParam Long seq
    ) throws Exception{
        return new ResponseEntity<>(mService.dday(seq), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "회원 이미지 등록", description = "토큰을 통해 회원 이미지 등록")
    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HiaResponseVO> putImg(
        @Parameter(description = "사진 파일") MultipartFile file,
        @Parameter(description = "회원토큰") String token
        ) {
        return new ResponseEntity<>(mService.addImgFile(file,token), HttpStatus.ACCEPTED);
    }
}
