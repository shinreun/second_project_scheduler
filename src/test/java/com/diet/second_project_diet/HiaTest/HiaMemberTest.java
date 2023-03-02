package com.diet.second_project_diet.HiaTest;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.repository.MemberInfoRepository;

@SpringBootTest
public class HiaMemberTest {
    @Autowired MemberInfoRepository mRepo;

    @BeforeEach
    @Test
    public void getMember(){
        MemberInfoEntity member = mRepo.findByMiSeq(1L);
        System.out.println(member);
    }


    @Test
    public void addMember(){
        MemberInfoEntity member = new MemberInfoEntity();
        member.setMiName("이름");
        member.setMiId("아이디");
        member.setMiPwd("비밀번호");
        member.setMiAge(24);
        member.setMiGen(1);
        member.setMiAddress("주소");
        member.setMiStatus(1);
        member.setMiTall(179);
        member.setMiWeight(90);
        member.setMiHard(2);
        member.setMiKcal(1500);
        member.setMiStartTime(LocalDate.of(2023,02,16));
        member.setMiEndTime(LocalDate.of(2023,05,02));
        member.setMiWater(7);
        member.setMiToken("토큰");
        member.setMiImg(null);
        member.setMiGoalKg(70d);
        mRepo.save(member);
        Assertions.assertEquals(member.getMiName(), "이름");
        Assertions.assertEquals(member.getMiId(), "아이디");
        Assertions.assertEquals(member.getMiPwd(), "비밀번호");
        Assertions.assertEquals(member.getMiAge(), 24);
        Assertions.assertEquals(member.getMiGen(), 1);
        Assertions.assertEquals(member.getMiAddress(), "주소");
        Assertions.assertEquals(member.getMiStatus(), 1);
        Assertions.assertEquals(member.getMiTall(), 179);
        Assertions.assertEquals(member.getMiWeight(), 90);
        Assertions.assertEquals(member.getMiHard(), 2);
        Assertions.assertEquals(member.getMiKcal(), 1500);
        Assertions.assertEquals(member.getMiStartTime(), LocalDate.of(2023,02,16));
        Assertions.assertEquals(member.getMiEndTime(), LocalDate.of(2023,05,02));
        Assertions.assertEquals(member.getMiWater(), 7);
        Assertions.assertEquals(member.getMiToken(), "토큰");
        Assertions.assertEquals(member.getMiImg(), null);
        Assertions.assertEquals(member.getMiGoalKg(), 70d);
    }


    @Test
    public void updateKcal(){
        MemberInfoEntity member = mRepo.findByMiSeq(1L);
        member.setMiKcal(1600);
        mRepo.save(member);
        Assertions.assertEquals(member.getMiKcal(), 1600);
    }


    @Test
    public void getDday() throws Exception{
        MemberInfoEntity member = mRepo.findByMiSeq(1L);
        String endDate = member.getMiEndTime().toString();
        String strDate = member.getMiStartTime().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(format.parse(endDate).getTime());
        Date today = new Date(format.parse(strDate).getTime());
        Long calculate = date.getTime() - today.getTime();
        int Ddays = (int) (calculate / (24*60*60*1000));
        System.out.println(Ddays);
    }
}
