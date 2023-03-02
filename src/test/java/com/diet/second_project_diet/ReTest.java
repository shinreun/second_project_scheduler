package com.diet.second_project_diet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diet.second_project_diet.entity.DayFoodEntity;
import com.diet.second_project_diet.entity.DietCalorieExEntity;
import com.diet.second_project_diet.entity.MemberInfoEntity;
import com.diet.second_project_diet.entity.DietSuggestEntity;
import com.diet.second_project_diet.entity.WeightInfoEntity;
import com.diet.second_project_diet.repository.DayFoodRepository;
import com.diet.second_project_diet.repository.DietSuggestRepository;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.DietCalorieExRepository;
import com.diet.second_project_diet.repository.WeightInfoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ReTest {
  @Autowired MemberInfoRepository memberRepo;
	@Autowired DietSuggestRepository suggestRepo;
	@Autowired DayFoodRepository dietRepo;
	@Autowired DietCalorieExRepository calRepo;
	@Autowired WeightInfoRepository weightRepo;
	
	// 식단 출력
	@BeforeEach
	@Test
	@Transactional
	public void get() {
		MemberInfoEntity member = new MemberInfoEntity();
		member.setMiName("사용자");
		member.setMiId("아이디");
		member.setMiPwd("비밀번호");
		member.setMiAge(24);
		member.setMiGen(0);
		member.setMiAddress("주소");
		member.setMiStatus(1);
		member.setMiTall(160);
		member.setMiWeight(50);
		member.setMiHard(1);
		member.setMiKcal(1200);
		member.setMiStartTime(LocalDate.of(2022, 4, 1));
		member.setMiEndTime(LocalDate.of(2022, 6, 1));
		member.setMiWater(8);
		member.setMiToken("100");
		member.setMiGoalKg(60d);
		memberRepo.save(member);
		DayFoodEntity dayfood = new DayFoodEntity(null, member, "메뉴", "image.jpg", LocalDateTime.now(), 120);
		dietRepo.save(dayfood);
		DayFoodEntity find = dietRepo.findByDfSeq(dayfood.getDfSeq());

		Assertions.assertEquals(find.getDfSeq(), dayfood.getDfSeq());
	}
	
	// 식단 삭제
	@Test
	@Transactional
	void deleteDailyDiet() {
		MemberInfoEntity member = new MemberInfoEntity();
		member.setMiName("사용자"); member.setMiId("아이디"); member.setMiPwd("비밀번호");
		member.setMiAge(24); member.setMiGen(0); member.setMiAddress("주소");
		member.setMiStatus(1); member.setMiTall(160); member.setMiWeight(50);
		member.setMiHard(1); member.setMiKcal(1200); member.setMiStartTime(LocalDate.of(2022, 4, 1));
		member.setMiEndTime(LocalDate.of(2022, 6, 1)); member.setMiWater(8); member.setMiToken("100");
		member.setMiGoalKg(60d);
		memberRepo.save(member);

		DayFoodEntity entity = new DayFoodEntity(null, member, "메뉴", "이미지", LocalDateTime.now(), 100);
		dietRepo.save(entity);
		DayFoodEntity dayfood = dietRepo.findByDfSeq(entity.getDfSeq());
		dietRepo.delete(dayfood);
		dietRepo.findById(dayfood.getDfSeq()).equals(null);
	}

	// 식단 수정
	@Test
	@Transactional
	void updateDailyDiet() {
		MemberInfoEntity member = new MemberInfoEntity();
		member.setMiName("사용자"); member.setMiId("아이디"); member.setMiPwd("비밀번호");
		member.setMiAge(24); member.setMiGen(0); member.setMiAddress("주소");
		member.setMiStatus(1); member.setMiTall(160); member.setMiWeight(50);
		member.setMiHard(1); member.setMiKcal(1200); member.setMiStartTime(LocalDate.of(2022, 4, 1));
		member.setMiEndTime(LocalDate.of(2022, 6, 1)); member.setMiWater(8); member.setMiToken("100");
		member.setMiGoalKg(60d);
		memberRepo.save(member);
		
		DayFoodEntity dayfood = new DayFoodEntity(null, member, "메뉴", "image.jpg", LocalDateTime.now(), 120);
		dayfood.setDfMenu("메뉴 수정");
		dietRepo.save(dayfood);
		Assertions.assertEquals(dietRepo.findByDfSeq(dayfood.getDfSeq()).getDfMenu(), "메뉴 수정");
	}
	
	// 식단 추가
	@Test
	@Transactional
	void addDailyDiet() {
		MemberInfoEntity member = new MemberInfoEntity();
		member.setMiName("사용자"); member.setMiId("아이디"); member.setMiPwd("비밀번호");
		member.setMiAge(24); member.setMiGen(0); member.setMiAddress("주소");
		member.setMiStatus(1); member.setMiTall(160); member.setMiWeight(50);
		member.setMiHard(1); member.setMiKcal(1200); member.setMiStartTime(LocalDate.of(2022, 4, 1));
		member.setMiEndTime(LocalDate.of(2022, 6, 1)); member.setMiWater(8); member.setMiToken("100");
		member.setMiGoalKg(60d);
		memberRepo.save(member);

		DayFoodEntity dayfood = new DayFoodEntity(null, member, "메뉴", "image.jpg", LocalDateTime.now(), 120);
		dietRepo.save(dayfood);
		Assertions.assertEquals(dietRepo.countByDfSeq(dayfood.getDfSeq()), 1);
	}
	
	// 추천 식단 출력
	@Test
	@Transactional
	public void getSuggestDiet() {
		DietSuggestEntity suggest = new DietSuggestEntity(null, "피부에 양보하세요", 1, 100, LocalDate.of(2023, 3, 9), 1);
		suggestRepo.save(suggest);
		DietSuggestEntity entity = suggestRepo.findByDietSeq(suggest.getDietSeq());
		Assertions.assertEquals(entity.getDietContent(), suggest.getDietContent());
	}

	// 식단 예시 출력
	@Test
	@Transactional
	public void getCalorieEx() {
		DietCalorieExEntity cal = new DietCalorieExEntity(null, "치킨", "chicken.jpg", 1200, null);
		calRepo.save(cal);
		DietCalorieExEntity entity = calRepo.findByDceSeq(cal.getDceSeq());
		Assertions.assertEquals(entity.getDceContent(), cal.getDceContent());
	}

	// 추천 식단 등록
	@Test
	@Transactional
	public void addSuggestDiet() {
		DietSuggestEntity suggest = new DietSuggestEntity(null, "피부에 양보하세요", 1, 100, LocalDate.of(2023, 3, 9), 1);
		suggestRepo.save(suggest);
		Assertions.assertEquals(suggest.getDietContent(), "피부에 양보하세요" );
		Assertions.assertEquals(suggest.getDietStatus(), 1);
		Assertions.assertEquals(suggest.getDietTotalCal(), 100);
	}

	// 식단 예시 등록
	@Test
	@Transactional
	public void addCalorieEx() {
		DietCalorieExEntity cal = new DietCalorieExEntity(null, "치킨", "chicken.jpg", 1200, null);
		calRepo.save(cal);
		DietCalorieExEntity entity = calRepo.findByDceSeq(cal.getDceSeq());
		Assertions.assertEquals(entity.getDceContent(), cal.getDceContent());
		Assertions.assertEquals(entity.getDceImage(), cal.getDceImage());
		Assertions.assertEquals(entity.getDceKcal(), cal.getDceKcal());
	}
	
	// 식단 예시를 선택해서 식단 입력
	@Test
	@Transactional
	public void addDietByCalEx() {
		MemberInfoEntity member = memberRepo.findByMiSeq(1L);
		DietCalorieExEntity cal = calRepo.findByDceSeq(1L);
		DayFoodEntity diet = new DayFoodEntity(null, member, cal.getDceContent(), cal.getDceImage() , LocalDateTime.now(), cal.getDceKcal());
		dietRepo.save(diet);
		Assertions.assertEquals(diet.getDfMenu(), cal.getDceContent() );
		Assertions.assertEquals(diet.getDfImg(), cal.getDceImage() );
		Assertions.assertEquals(diet.getDfKcal(), cal.getDceKcal() );
	}
	
	// 체중 입력
	@Test
	@Transactional
	public void addWeight() {
		MemberInfoEntity member = memberRepo.findByMiSeq(1L);
		WeightInfoEntity weight = new WeightInfoEntity(null, member, 60d, LocalDate.now());
		weightRepo.save(weight);
		Assertions.assertEquals(weight.getWeiWeight(), 60);
		Assertions.assertEquals(weight.getMember(), member);
		Assertions.assertEquals(weight.getWeiDate(), LocalDate.now());
	}

	// 체중 수정
	@Test
	@Transactional
	public void updateWeight() {
		MemberInfoEntity member = memberRepo.findByMiSeq(1L);
		WeightInfoEntity weight = new WeightInfoEntity(null, member, 60d, LocalDate.now());
		weightRepo.save(weight);
		weight.setWeiWeight(85d);
		Assertions.assertEquals(weight.getWeiWeight(), 85);
	}

	// 체중 출력
	@Test
	@Transactional
	public void getWeight() {
		MemberInfoEntity member = memberRepo.findByMiSeq(1L);
		WeightInfoEntity weight = new WeightInfoEntity(null, member, 60d, LocalDate.now());
		weightRepo.save(weight);
		weight.setWeiWeight(85d);
		Assertions.assertEquals(weight.getWeiWeight(), 85);
	}
	
	// 체중 삭제
	@Transactional
	@Test
	public void deleteWeight() {
		MemberInfoEntity member = memberRepo.findByMiSeq(1L);
		WeightInfoEntity weight = new WeightInfoEntity(null, member, 60d, LocalDate.now());
		weightRepo.save(weight);
		weightRepo.delete(weight);
		weightRepo.findById(weight.getWeiSeq()).equals(null);
	}
}
