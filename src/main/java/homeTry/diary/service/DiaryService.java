package homeTry.diary.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import homeTry.diary.dto.DiaryDto;
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

    public DiaryDto getDiaryByDate(int year, int month, int day) {

        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.MAX);

        Diary diary = diaryRepository.findByDate(startOfDay, endOfDay)
            .orElseThrow(() -> new NoSuchElementException());
        
        return new DiaryDto(diary.getId(), diary.getCreateAt(), diary.getMemo().toString(), diary.getMemberEmail().toString());
    }

    @Transactional
    public void createDiary(DiaryRequest diaryRequest) {
        
        //token에서 추출
        String email = "sample";
        
        diaryRepository.save(
                new Diary(LocalDateTime.now(),
                        diaryRequest.memo(),
                        email));
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
