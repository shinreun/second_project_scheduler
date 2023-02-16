package com.diet.second_project_diet.pill.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diet.second_project_diet.pill.service.DhPillInfoService;
import com.diet.second_project_diet.pill.vo.DhPillInfoInsertVO;
import com.diet.second_project_diet.pill.vo.DhResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "약 정보 관리", description = "약 정보 CRUD")
@RestController
@RequestMapping("/api/pill")
@RequiredArgsConstructor
public class DhPillAPIController {
    private final DhPillInfoService piService;

    @Operation(summary = "약 추가 / 로그인(토큰)x", description = "약을 등록합니다.")
    @PutMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DhResponseVO> putAddPillInfo(@RequestBody DhPillInfoInsertVO data) {
        return new ResponseEntity<>(piService.addPillInfo(data), HttpStatus.OK);
    }


}