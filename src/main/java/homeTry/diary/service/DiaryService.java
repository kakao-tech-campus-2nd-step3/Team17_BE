package homeTry.diary.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;

import org.apache.coyote.BadRequestException;
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

        LocalDate targetDay = LocalDate.of(year, month, day);

        Diary diary = diaryRepository.findByDateAndMemberEmail(targetDay, new Email(memberEmail))
            .orElseThrow(() ->new IllegalStateException("존재하지 않는 일기입니다."));
        
        return new DiaryDto(diary.getId(), diary.getCreateAt(), diary.getMemo().toString(), diary.getMemberEmail().toString());
    }

    @Transactional
    public void createDiary(DiaryRequest diaryRequest, String memberEmail) {
        
        if (diaryRepository.findByDateAndMemberEmail(LocalDate.now(), new Email(memberEmail)).isPresent()) {
            throw new IllegalStateException("오늘은 이미 일기를 작성했습니다.");
        }

        diaryRepository.save(
                new Diary(LocalDate.now(),
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
