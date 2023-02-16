package com.diet.second_project_diet.pill.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.diet.second_project_diet.entity.PillInfoCompleteEntity;
import com.diet.second_project_diet.entity.PillInfoEntity;
import com.diet.second_project_diet.pill.vo.DhPillInfoInsertVO;
import com.diet.second_project_diet.pill.vo.DhResponseVO;
import com.diet.second_project_diet.repository.MemberInfoRepository;
import com.diet.second_project_diet.repository.PillInfoRepository;
import com.diet.second_project_diet.repository.PillinfocompleteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DhPillInfoService {
    private final MemberInfoRepository memberRepo;
    private final PillInfoRepository pillRepo;
    private final PillinfocompleteRepository pcRepo;


    // @Operation(summary = "약 추가 / 로그인(토큰)x", description = "약을 등록합니다.")
    public DhResponseVO addPillInfo(DhPillInfoInsertVO data) {
        // Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        DhResponseVO response = new DhResponseVO();
        PillInfoEntity entity = PillInfoEntity.builder()
                .piName(data.getPiName())
                .piAmount(data.getPiAmount())
                .piStatus(0)
                .member(memberRepo.findById(data.getMember()).get())
                .build();
        pillRepo.save(entity);
        PillInfoCompleteEntity entity2 = PillInfoCompleteEntity.builder()
                .picTotal(entity.getPiAmount())
                .pill(entity)
                .picGoal(0)
                .picDate(LocalDate.now())
                .build();
        pcRepo.save(entity2);
        response = DhResponseVO.builder()
                .status(false)
                .message("약 정보가 추가되었습니다.").build();
        return response;
    }

}