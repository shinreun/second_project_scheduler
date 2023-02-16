package com.diet.second_project_diet.water.vo;

import java.util.Date;


import lombok.Data;








@Data
public class mgAddWaterVO {
    private Integer wiCount;
    private Date wiDate;


    
    // private Long dayWater;
    // private String sumWater; // 이 이름이 어떤 정보를 가져오는지의 조건을 적어주기
    // // 합계하는 조건을 적고 

    // public mgReadWaterVO (WaterInfoEntity entity) {
    //     this.sumWater = entity.getWaterInfoEntity().getsumWater();

    // }
    
    // public ClosePickupTimeVO(StoreTimeDetailEntity entity) {
    //     this.utiSeq = entity.getUnivTimeInfoEntity().getUtiSeq();
    //     this.name = entity.getUnivTimeInfoEntity().getUtiName();
    //     this.closeTime = entity.getStdCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    //     this.pickupTime = entity.getUnivTimeInfoEntity().getUtiPickupTime1().format(DateTimeFormatter.ofPattern("HH:mm"));
    //     this.thisTime = false;
    // } 어떤 형식으로 가져오겠다 적기

    
}
