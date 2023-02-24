package com.diet.second_project_diet.member.vo;

import com.diet.second_project_diet.entity.MemberInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiaAddMemberInfoVO {
    @Schema(description = "아이디", example = "user01")
    private String id;
    @Schema(description = "비밀번호", example = "123456")
    private String pwd;
    @Schema(description = "이름", example = "사용자01")
    private String name;
    @Schema(description = "나이", example = "26")
    private Integer age;
    @Schema(description = "성별", example = "1")
    private Integer gen;
    @Schema(description = "주소", example = "대구광역시 중구")
    private String address;
    @Schema(description = "키", example = "180")
    private Integer tall;
    @Schema(description = "몸무게", example = "80")
    private Integer weight;
    @Schema(description = "다이어트 강도", example = "1")
    private Integer hard;
    @Schema(description = "목표 칼로리", example = "1500")
    private Integer cal;
    @Schema(description = "목표 몸무게", example = "70")
    private Double kg;
    @Schema(description = "목표 음수량", example = "8")
    private Integer water;
    @Schema(description = "목표 기간", example = "100")
    private Integer time;
    
    // public Integer getAge(MemberInfoEntity member){
    //     Calendar now = Calendar.getInstance();
    //     Integer currentYear = now.get(Calendar.YEAR);

    //     SimpleDateFormat format = new SimpleDateFormat("yyyy");
    //     String stringBirthYear = format.format(member.getMiBirth());
    //     Integer birthYear = Integer.parseInt(stringBirthYear);
    //     return (currentYear - birthYear +1);
    // }

    public HiaAddMemberInfoVO(MemberInfoEntity member){
        this.id = member.getMiId();
        this.pwd = member.getMiPwd();
        this.name = member.getMiName();
        this.age = member.getMiAge();  
        this.gen = member.getMiGen();
        this.address = member.getMiAddress();
        this.tall = member.getMiTall();
        this.weight = member.getMiWeight();
        this.hard = member.getMiHard();
        this.cal = member.getMiKcal();
        this.kg = member.getMiGoalKg();
        this.water = member.getMiWater();
    }
}
