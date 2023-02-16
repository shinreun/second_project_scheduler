package com.diet.second_project_diet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.DietCalorieExEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.DietSuggestEntity;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.DietSuggestRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.DietCalorieExRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ReTest {
  @Autowired MemberInfoRepository memberRepo;
	@Autowired DietSuggestRepository suggestRepo;
	@Autowired DayFoodRepository dietRepo;
	@Autowired DietCalorieExRepository calRepo;
	
	@BeforeEach
	@Test
	@Transactional
	public void get() {
		MemberInfoEntity member = new MemberInfoEntity();
		member.setMiSeq(100L); member.setMiName("사용자"); member.setMiId("아이디"); member.setMiPwd("비밀번호");
		member.setMiBirth(LocalDate.of(2023, 3, 9)); member.setMiGen(0); member.setMiAddress("주소");
		member.setMiStatus(1); member.setMiTall(160); member.setMiWeight(50); member.setMiHard(1);
		member.setMiKcal(1200); member.setMiStartTime(LocalDate.of(2022, 4, 1)); member.setMiWater(8);
		member.setMiToken("100"); memberRepo.save(member);
		DayFoodEntity dayfood = new DayFoodEntity(null, member, "메뉴", "image.jpg", LocalDateTime.now(), 120);
		dietRepo.save(dayfood);
		DayFoodEntity find = dietRepo.findByDfSeq(dayfood.getDfSeq());

		Assertions.assertEquals(find.getDfSeq(), dayfood.getDfSeq());
	}
	
	@Test
	void deleteDailyDiet() {
		MemberInfoEntity member = new MemberInfoEntity();
		member.setMiSeq(100L); member.setMiName("사용자"); member.setMiId("아이디"); member.setMiPwd("비밀번호");
		member.setMiBirth(LocalDate.of(2023, 3, 9)); member.setMiGen(0); member.setMiAddress("주소");
		member.setMiStatus(1); member.setMiTall(160); member.setMiWeight(50); member.setMiHard(1);
		member.setMiKcal(1200); member.setMiStartTime(LocalDate.of(2022, 4, 1)); member.setMiWater(8);
		member.setMiToken("100"); memberRepo.save(member);

		DayFoodEntity entity = new DayFoodEntity(null, member, "메뉴", "이미지", LocalDateTime.now(), 100);
		dietRepo.save(entity);
		DayFoodEntity dayfood = dietRepo.findByDfSeq(entity.getDfSeq());
		dietRepo.delete(dayfood);
		dietRepo.findById(dayfood.getDfSeq()).equals(null);
	}


	@Test
	void updateDailyDiet() {
		MemberInfoEntity member = new MemberInfoEntity();
		member.setMiSeq(100L); member.setMiName("사용자"); member.setMiId("아이디"); member.setMiPwd("비밀번호");
		member.setMiBirth(LocalDate.of(2023, 3, 9)); member.setMiGen(0); member.setMiAddress("주소");
		member.setMiStatus(1); member.setMiTall(160); member.setMiWeight(50); member.setMiHard(1);
		member.setMiKcal(1200); member.setMiStartTime(LocalDate.of(2022, 4, 1)); member.setMiWater(8);
		member.setMiToken("100"); memberRepo.save(member);

		DayFoodEntity dayfood = new DayFoodEntity(null, member, "메뉴", "image.jpg", LocalDateTime.now(), 120);
		dayfood.setDfMenu("메뉴 수정");
		dietRepo.save(dayfood);
		Assertions.assertEquals(dietRepo.findByDfSeq(dayfood.getDfSeq()).getDfMenu(), "메뉴 수정");
	}
	
	@Test
	void addDailyDiet() {
		MemberInfoEntity member = new MemberInfoEntity();
		member.setMiSeq(100L); member.setMiName("사용자"); member.setMiId("아이디"); member.setMiPwd("비밀번호");
		member.setMiBirth(LocalDate.of(2023, 3, 9)); member.setMiGen(0); member.setMiAddress("주소");
		member.setMiStatus(1); member.setMiTall(160); member.setMiWeight(50); member.setMiHard(1);
		member.setMiKcal(1200); member.setMiStartTime(LocalDate.of(2022, 4, 1)); member.setMiWater(8);
		member.setMiToken("100"); memberRepo.save(member);

		DayFoodEntity dayfood = new DayFoodEntity(null, member, "메뉴", "image.jpg", LocalDateTime.now(), 120);
		dietRepo.save(dayfood);
		Assertions.assertEquals(dietRepo.countByDfSeq(dayfood.getDfSeq()), 1);
	}
	
	@Test
	public void getSuggestDiet() {
		DietSuggestEntity suggest = new DietSuggestEntity(null, "피부에 양보하세요", 1, 100, LocalDate.of(2023, 3, 9), 1);
		suggestRepo.save(suggest);
		Optional<DietSuggestEntity> entity = suggestRepo.findById(suggest.getDietSeq());
		Assertions.assertEquals(entity.get().getDietContent(), suggest.getDietContent());
	}

	@Test
	public void getCalorieEx() {
		DietCalorieExEntity cal = new DietCalorieExEntity(null, "치킨", "chicken.jpg", 1200);
		calRepo.save(cal);
		DietCalorieExEntity entity = calRepo.findByDceSeq(cal.getDceSeq());
		Assertions.assertEquals(entity.getDceContent(), cal.getDceContent());
	}
}
