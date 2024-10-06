package homeTry.diary.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import homeTry.diary.dto.DiaryDto;
import java.util.List;

import java.time.LocalDate;

import homeTry.member.service.MemberService;
import homeTry.diary.dto.request.DiaryRequest;
import homeTry.diary.exception.DiaryNotFoundException;
import homeTry.diary.model.entity.Diary;
import homeTry.diary.repository.DiaryRepository;
import jakarta.transaction.Transactional;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberService memberService;

    public DiaryService(DiaryRepository diaryRepository, MemberService memberService) {
        this.diaryRepository = diaryRepository;
        this.memberService = memberService;
    }

    public List<DiaryDto> getDiaryByDate(LocalDate date, Long memberId) {

        //time 상수 추가시 추가 리팩토링 예정
        LocalDateTime startOfDay = LocalDate.now().atTime(3, 0, 0);
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atTime(2, 59, 59);

        List<Diary> diaries = diaryRepository.findByDateRangeAndMember(startOfDay, endOfDay, memberService.getMemberEntity(memberId));

        return diaries
                .stream()
                .map(DiaryDto::convertToDiaryDto)
                .toList();

    }

    @Transactional
    public void createDiary(DiaryRequest diaryRequest, Long memberId) {

        diaryRepository.save(
                new Diary(diaryRequest.memo(),
                        memberService.getMemberEntity(memberId)));
    }

    @Transactional
    public void deleteDiary(Long diaryId) {

        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new DiaryNotFoundException());
        
        diaryRepository.delete(diary);
        
    }
}
