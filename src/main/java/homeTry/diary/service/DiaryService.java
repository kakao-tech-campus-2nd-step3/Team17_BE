package homeTry.diary.service;

import homeTry.common.constants.DateTimeUtil;
import homeTry.diary.dto.DiaryDto;
import homeTry.diary.dto.request.DiaryRequest;
import homeTry.diary.exception.badRequestException.DiaryNotFoundException;
import homeTry.diary.model.entity.Diary;
import homeTry.diary.repository.DiaryRepository;
import homeTry.member.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberService memberService;

    public DiaryService(DiaryRepository diaryRepository, MemberService memberService) {
        this.diaryRepository = diaryRepository;
        this.memberService = memberService;
    }

    @Transactional(readOnly = true)
    public Slice<DiaryDto> getDiaryByDate(LocalDate date, Long memberId, Pageable pageable) {

        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(date);
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(date);

        Slice<Diary> diaries = diaryRepository.findByCreatedAtBetweenAndMember(
                startOfDay, endOfDay, memberService.getMemberEntity(memberId), pageable);

        return diaries.map(DiaryDto::from);
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

    @Transactional
    public void deleteByMember(Long memberId) {
        diaryRepository.deleteByMember(memberService.getMemberEntity(memberId));
    }
}
