package com.diet.second_project_diet.pill.controller;
// package com.diet.pill.controller;

// import java.util.Map;

// import org.springframework.data.domain.Pageable;
// import org.springframework.data.web.PageableDefault;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.diet.pill.service.DhPillInfoService;
// import com.diet.pill.vo.DhPillInfoInsertVO;

// import io.micrometer.common.lang.Nullable;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import net.bytebuddy.TypeCache.Sort;

// @RestController
// @RequestMapping("/api/pill")
// public class DhPillController {
//     // private final ArtistGroupInfoService agiService;
//     private final DhPillInfoService piService;

//     @Operation(summary = "약정보 추가", description="아티스트 그룹을 추가합니다.")
//     @PutMapping(value = "/insert", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<Object> putArtistGroupInfo(
//             @Parameter(description = "formdata로 데이터를 입력합니다. (name:그룹명, debutYear:데뷔연도,company=기획사 번호)")
//             // ArtistGroupInfoInsertVO data,
//         DhPillInfoInsertVO data
//     ) {
//         Map<String, Object> map = piService.addArtistGroupInfo(data);
//         return new ResponseEntity<>(map, HttpStatus.OK);
//     }

//     @GetMapping("/list")
//     // public ResponseEntity<ArtistGroupResponseVO> getArtistGroupList(
//     public ResponseEntity<DhPillInfoInsertVO> getArtistGroupList(
//         @RequestParam @Nullable String keyword,
//         @PageableDefault(size = 10, sort = "agiSeq", direction = Sort.direction.DESC)
//         Pageable pageable
//     ) {
//         return new ResponseEntity<>(piService.getArtistGroupList(keyword, pageable), HttpStatus.OK);
//     }

// }
