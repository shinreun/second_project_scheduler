package com.diet.second_project_diet.water.api;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.diet.second_project_diet.repository.WaterInfoRepository;
import com.diet.second_project_diet.water.service.mgWaterService;
import com.diet.second_project_diet.water.vo.mgWaterResponseVO;
import com.diet.second_project_diet.water.vo.mgWeekListWaterVO;
import com.diet.second_project_diet.water.vo.mgWeeklyListVO;
import com.diet.second_project_diet.water.vo.mgDayWaterVO;

import org.springframework.ui.Model;

@RestController
@RequestMapping("/api/water")
public class mgWaterController {
    @Autowired mgWaterService wService;


    //수정  증가
     @PostMapping("/update") 
     public ResponseEntity<mgWaterResponseVO > postUpdateWater(@RequestParam String token, LocalDate wiDate) {
       //Map<String, Object> resultMap = wService.updateWater(wiSeq,wiDate);
        return new ResponseEntity<>(wService.updateWater(token,wiDate),HttpStatus.ACCEPTED);
     }
     // 수정 감소 
     @PostMapping("/minus") 
     public ResponseEntity<mgWaterResponseVO> postMinusWater(@RequestParam String token, LocalDate wiDate ) {
        // Map<String, Object> resultMap = wService.minusWater(wiSeq,wiDate);
        return new ResponseEntity<>(wService.minusWater(token, wiDate), HttpStatus.ACCEPTED);
        //return new ResponseEntity<> (wService.minusWater(wiSeq, wiDate) ,HttpStatus.ACCEPTED);
     }

         // 그냥 월~일 음수량..(조회)
      @GetMapping("/day")
      public ResponseEntity<mgDayWaterVO> getWaterList(@Parameter @RequestParam String token, @Parameter LocalDate date) {
     
      return new ResponseEntity<>(wService.weekWater(token, date), HttpStatus.ACCEPTED);
      // 리턴을 잘못함. html 방법임. map 으로 변경하는 걸로 하기
      }
      
      @GetMapping("/week")
      public ResponseEntity<mgWeeklyListVO> getWeekWaterList(@Parameter @RequestParam String token, @Parameter LocalDate date) {
      return new ResponseEntity<>(wService.weekWaterList(token, date), HttpStatus.ACCEPTED);
      // 리턴을 잘못함. html 방법임. map 으로 변경하는 걸로 하기
      }
      


      }

    //  // 총 물 개수가 나옴
    //  @GetMapping("/list") 
    //  public ResponseEntity<Object> getCountWater(Long wiSeq) {
    //     Map<String,Object> resultMap = wService.countWater(wiSeq);
    //     return new ResponseEntity<Object>(resultMap,(HttpStatus) resultMap.get("code"));
    //  }

    //     //월~금 총 음수량(합계)
    // @GetMapping("/total")
    // public ResponseEntity<Object> getCountWater(@RequestParam Long wiCount) {
    // Map<String, Object> resultMap = wService.countWater(wiCount);
    // return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    
    // }



// //추가를 만들려고 헀음(섭취량)
//     @PostMapping("/add")
//     public ResponseEntity<Object> postWater(@RequestBody mgAddWaterVO data) {
//           Map<String, Object> resultMap = wService.addWater(data);
//         return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
//     }





