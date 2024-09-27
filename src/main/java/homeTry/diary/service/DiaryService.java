package homeTry.diary.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import homeTry.diary.dto.DiaryDto;

import homeTry.member.model.vo.Email;

import homeTry.diary.dto.request.DiaryRequest;
import homeTry.diary.model.entity.Diary;
import homeTry.diary.repository.DiaryRepository;
import jakarta.transaction.Transactional;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public DiaryDto getDiaryByDate(int year, int month, int day, String memberEmail) {

        LocalDateTime startOfDay = LocalDateTime.of(year, month, day, 0, 0, 0); // 시작 시간
        LocalDateTime endOfDay = LocalDateTime.of(year, month, day, 23, 59, 59); // 끝 시간

        Diary diary = diaryRepository.findByDateRangeAndMemberEmail(startOfDay, endOfDay, new Email(memberEmail))
            .orElseThrow(() ->new IllegalStateException("존재하지 않는 일기입니다."));
        
        return new DiaryDto(diary.getId(), diary.getCreateAt(), diary.getMemo().toString(), diary.getMemberEmail().toString());
     }

    @Transactional
    public void createDiary(DiaryRequest diaryRequest, String memberEmail) {
        
        diaryRepository.save(
                new Diary(LocalDateTime.now(),
                        diaryRequest.memo(),
                        memberEmail));
    }

    @Transactional
    public void deleteDiary(Long diaryId){

        if(diaryRepository.findById(diaryId).isEmpty()) {
            throw new NoSuchElementException();
        } else {
          diaryRepository.deleteById(diaryId);
        }
    }
}
