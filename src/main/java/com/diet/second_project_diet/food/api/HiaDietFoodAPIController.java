package com.diet.second_project_diet.food.api;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diet.second_project_diet.food.service.HiaDietFoodService;
import com.diet.second_project_diet.food.vo.HiaDayTotalCalVO;

@RestController
@RequestMapping("/api/diet/food")
public class HiaDietFoodAPIController {
    @Autowired HiaDietFoodService dfService;

    @PutMapping("/sum")
    public ResponseEntity<Object> postDietFood(@RequestBody HiaDayTotalCalVO data){
        Map<String, Object> map = dfService.sumTotalCal(data);
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
}
