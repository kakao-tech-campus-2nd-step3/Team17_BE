package homeTry.diary.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import homeTry.diary.dto.DiaryDto;
import java.util.List;

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

    public List<DiaryDto> getDiaryByDate(int year, int month, int day, Long memberId) {

        LocalDateTime startOfDay = LocalDateTime.of(year, month, day, 0, 0, 0); // 시작 시간
        LocalDateTime endOfDay = LocalDateTime.of(year, month, day, 23, 59, 59); // 끝 시간

        List<Diary> diaries = diaryRepository.findByDateRangeAndMember(startOfDay, endOfDay, memberService.getMemberEntity(memberId));

        return diaries.stream().map(DiaryDto::convertToDiaryDto).toList();

    }

    @Transactional
    public void createDiary(DiaryRequest diaryRequest, Long memberId) {

        diaryRepository.save(
                new Diary(LocalDateTime.now(),
                        diaryRequest.memo(),
                        memberService.getMemberEntity(memberId)));
    }

    @Transactional
    public void deleteDiary(Long diaryId) {

        if (diaryRepository.findById(diaryId).isEmpty()) {
            throw new DiaryNotFoundException();
        } else {
            diaryRepository.deleteById(diaryId);
        }
    }
}
