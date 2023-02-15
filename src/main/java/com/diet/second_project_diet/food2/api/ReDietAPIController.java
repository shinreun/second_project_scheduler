// package com.diet.second_project_diet.food2.api;

// import java.net.URLEncoder;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;
// import java.util.Calendar;
// import java.util.LinkedHashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.io.Resource;
// import org.springframework.core.io.UrlResource;
// import org.springframework.data.repository.query.Param;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestPart;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.diet.second_project_diet.entity.DayFoodEntity;
// import com.diet.second_project_diet.food2.service.FileService;
// import com.diet.second_project_diet.food2.service.ReDietService;
// import com.diet.second_project_diet.food2.vo.ReDietInsertVO;
// import com.diet.second_project_diet.repository.DayFoodRepository;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.parameters.RequestBody;
// import jakarta.servlet.http.HttpServletRequest;
// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/diet")
// @RequiredArgsConstructor
// public class ReDietAPIController {
//   @Value("${file.image.dailydiet}") String diet_image_path;
//   private final ReDietService dService;
//   private final DayFoodRepository dietRepo;
//   private final FileService fileService;
  
//   @Operation(summary = "식단 추가", description = "식단과 사진을 등록합니다.")
//   @PutMapping(value = "/diet/add", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
//   public ResponseEntity<Object> putOwnerInfo(
//     @Parameter(description = "식단 정보") @RequestBody ReDietInsertVO data,
//     @Parameter(description = "사진 파일") MultipartFile file,
//     @Parameter(description = "회원 토큰") String token) {
//     Map<String, Object> map = dService.addDailyDiet(data, file, token);
//     return new ResponseEntity<>(map, HttpStatus.OK);
//   }

//   // 식단 사진 다운로드
//   @GetMapping("/images/{uri}") 
//   public ResponseEntity<Resource> getImage(@PathVariable String uri, HttpServletRequest request) throws Exception {
//     return fileService.getImageFile(uri);
//   }
// }
