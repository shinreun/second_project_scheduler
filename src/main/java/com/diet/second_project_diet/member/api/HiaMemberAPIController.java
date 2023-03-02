package com.diet.second_project_diet.member.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diet.second_project_diet.member.service.HiaFileService;
import com.diet.second_project_diet.member.service.HiaMemberService;
import com.diet.second_project_diet.member.vo.HiaAddMemberInfoVO;
import com.diet.second_project_diet.member.vo.HiaDataResponseVO;
import com.diet.second_project_diet.member.vo.HiaResponseVO;
import com.diet.second_project_diet.member.vo.HiaTimeResponseVO;
import com.diet.second_project_diet.member.vo.ReLoginRequestVO;
import com.diet.second_project_diet.member.vo.ReLoginVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;


@Tag(name = "회원정보 관리", description = "회원정보 CRUD 관리")
@RestController
@RequestMapping("/api/member")
public class HiaMemberAPIController {
    @Autowired HiaMemberService mService;
    @Autowired HiaFileService fileService;

    @Operation(summary = "회원정보 조회", description = "마이페이지에서 회원정보 조회기능")
    @GetMapping("/info")
    public ResponseEntity<HiaDataResponseVO> getMemberInfo(
        @Parameter(description = "회원토큰", example="1") @RequestParam String token){
        return new ResponseEntity<>(mService.getMemberInfo(token), HttpStatus.ACCEPTED);
    }    

    @Operation(summary = "회원정보 등록", description = "회원정보 등록(회원가입)")
    @PutMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HiaResponseVO> putMemberAdd (
    @Parameter(description = "회원 정보") HiaAddMemberInfoVO data,
    @Parameter(description = "사진 파일") MultipartFile file) {
        return new ResponseEntity<>(mService.addMemberInfo(data, file), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표 칼로리 변경", description = "회원별 목표 칼로리 변경")
    @PatchMapping("/update/kcal")
    public ResponseEntity<HiaResponseVO> patchGoalKcal(
    @Parameter(description = "변경 칼로리") @RequestParam Integer cal,    
    @Parameter(description = "회원 토큰") @RequestParam String token){
        return new ResponseEntity<>(mService.updateGoalKcal(cal, token), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표  몸무게 변경", description = "회원별 목표 몸무게 변경")
    @PatchMapping("/update/kg")
    public ResponseEntity<HiaResponseVO> patchGoalKg(
    @Parameter(description = "변경 몸무게") @RequestParam Double weight,
    @Parameter(description = "회원 토큰") @RequestParam String token){
        return new ResponseEntity<>(mService.updateGoalKg(weight, token), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표 음수량 변경", description = "회원별 목표 음수량 변경")
    @PatchMapping("/update/water")
    public ResponseEntity<HiaResponseVO> patchGoalWater(
    @Parameter(description = "변경 음수량") @RequestParam Integer water,
    @Parameter(description = "회원 토큰") @RequestParam String token){
        return new ResponseEntity<>(mService.updateGoalWater(water, token), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표 날짜 변경", description = "회원별 목표 날짜 변경")
    @PatchMapping("/update/day")
    public ResponseEntity<HiaResponseVO> patchGoalDay(
    @Parameter(description = "변경 목표 기간") @RequestParam Integer time,
    @Parameter(description = "회원 토큰") @RequestParam String token){
        return new ResponseEntity<>(mService.updateGoalDay(time, token), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "목표 다이어트 강도 변경", description = "회원별 목표 다이어트 강도 변경")
    @PatchMapping("/update/hard")
    public ResponseEntity<HiaResponseVO> patchHard(
    @Parameter(description = "변경 다이어트 강도") @RequestParam Integer hard,
    @Parameter(description = "회원 토큰") @RequestParam String token){
        return new ResponseEntity<>(mService.updateHard(hard, token), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "회원 탈퇴", description = "회원탈퇴 시 상태 값 변경")
    @PatchMapping("/delete")
    public ResponseEntity<HiaResponseVO> patchMemberStatus(
    @Parameter(description = "회원 토큰") @RequestParam String token){
        return new ResponseEntity<>(mService.deleteMember(token), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "D-Day 출력", description = "회원번호 입력 하면 D-Day 계산")
    @GetMapping("/dDay")
    public ResponseEntity<HiaTimeResponseVO> getDday(
        @Parameter(description = "회원 토큰", example = "1") @RequestParam String token
    ) throws Exception{
        return new ResponseEntity<>(mService.dday(token), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "회원 이미지 수정", description = "토큰을 통해 회원 이미지 수정")
    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HiaResponseVO> putImg(
        @Parameter(description = "사진 파일") MultipartFile file,
        @Parameter(description = "회원토큰") String token
    ) {
        return new ResponseEntity<>(mService.updateImgFile(file, token), HttpStatus.ACCEPTED);
    }
    
    @Operation(summary = "로그인", description = "아이디와 비밀번호를 통해 로그인")
    @PostMapping("/login")
    public ResponseEntity<ReLoginVO> Login(
        @Parameter(description = "로그인 정보") @RequestBody ReLoginRequestVO data
    ) {
        return new ResponseEntity<>(mService.Login(data), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "회원 이미지 가져오기", description = "회원 이미지 가져오기")
    @GetMapping("/image/{uri}")
    public ResponseEntity<Resource>getMemberImg(@Parameter(description = "사진 uri", example = "member.jpg") @PathVariable String uri, HttpServletRequest request)
    throws Exception{
        return fileService.getImagFile(uri); 
    }

}
