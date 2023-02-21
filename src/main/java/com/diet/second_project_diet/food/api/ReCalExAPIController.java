package com.diet.second_project_diet.food.api;

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

import com.diet.second_project_diet.food.service.ReDietService;
import com.diet.second_project_diet.food.service.ReFileService;
import com.diet.second_project_diet.food.vo.ReDietCalorieExInsertVO;
import com.diet.second_project_diet.food.vo.ReDietCalorieInsertResponseVO;
import com.diet.second_project_diet.food.vo.ReDietCalorieResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/calex")
@RequiredArgsConstructor
@Tag(name = "식단 예시 정보 관리", description = "Daily Diet Example CRUD")
public class ReCalExAPIController {
  @Value("${file.image.dailydiet}")
  String diet_image_path;
  private final ReDietService dService;
  private final ReFileService fileService;

  
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

  
  @Operation(summary = "식단 예시 검색 기능", description = "키워드를 포함하고 있는 식단예시를 검색합니다.")
  @GetMapping("/search/cal") 
  public ResponseEntity<ReDietCalorieResponseVO> addDietByCalEx(
      @Parameter(description = "검색 키워드", example = "치킨") String keyword) {
    return new ResponseEntity<>(dService.searchCalorieEx(keyword), HttpStatus.OK);
  }
 
}

