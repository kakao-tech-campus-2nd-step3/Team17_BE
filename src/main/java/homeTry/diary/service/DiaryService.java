package homeTry.diary.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import homeTry.common.constants.DateTimeUtil;
import homeTry.diary.dto.DiaryDto;
import java.util.List;

import java.time.LocalDate;

import homeTry.member.service.MemberService;
import homeTry.diary.dto.request.DiaryRequest;
import homeTry.diary.exception.BadRequestException.DiaryNotFoundException;
import homeTry.diary.model.entity.Diary;
import homeTry.diary.repository.DiaryRepository;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberService memberService;

    public DiaryService(DiaryRepository diaryRepository, MemberService memberService) {
        this.diaryRepository = diaryRepository;
        this.memberService = memberService;
    }

    @Transactional(readOnly = true)
    public List<DiaryDto> getDiaryByDate(LocalDate date, Long memberId) {

        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(date);
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(date);

        List<Diary> diaries = diaryRepository.findByCreatedAtBetweenAndMember(startOfDay, endOfDay,
            memberService.getMemberEntity(memberId));

        return diaries
            .stream()
            .map(DiaryDto::from)
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
