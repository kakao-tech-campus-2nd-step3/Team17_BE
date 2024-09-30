package homeTry.diary.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import homeTry.diary.dto.DiaryDto;

import homeTry.member.service.MemberService;
import homeTry.diary.dto.request.DiaryRequest;
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

    public DiaryDto getDiaryByDate(int year, int month, int day, Long memberId) {

        LocalDateTime startOfDay = LocalDateTime.of(year, month, day, 0, 0, 0); // 시작 시간
        LocalDateTime endOfDay = LocalDateTime.of(year, month, day, 23, 59, 59); // 끝 시간

        Diary diary = diaryRepository.findByDateRangeAndMember(startOfDay, endOfDay, memberService.getMemberEntity(memberId))
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 일기입니다."));

        return new DiaryDto(diary.getId(), diary.getCreateAt(), diary.getMemo().toString(), diary.getMember().getEmail().toString());
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
            throw new NoSuchElementException();
        } else {
            diaryRepository.deleteById(diaryId);
        }
    }
}
