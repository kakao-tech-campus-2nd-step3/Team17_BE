package homeTry.exerciseList.service;

import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseTimeRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseTimeService {

    private final ExerciseTimeRepository exerciseTimeRepository;

    public ExerciseTimeService(ExerciseTimeRepository exerciseTimeRepository) {
        this.exerciseTimeRepository = exerciseTimeRepository;
    }

    @Transactional
    public void saveExerciseTime(ExerciseTime exerciseTime) {
        exerciseTimeRepository.save(exerciseTime);
    }

    @Transactional
    public void resetExerciseTime(ExerciseTime exerciseTime) {
        exerciseTime.resetExerciseTime();
        saveExerciseTime(exerciseTime);
    }

    // 당일의 운동 시간 반환
    @Transactional(readOnly = true)
    public Duration getExerciseTimesForToday(Long memberId) {
        LocalDateTime startOfDay = LocalDate.now().atTime(3, 0, 0);
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atTime(2, 59, 59);

        // 해당 멤버의 당일 운동 시간 목록 조회
        List<ExerciseTime> exerciseTimes = exerciseTimeRepository.findByMemberIdAndStartTimeBetween(memberId, startOfDay, endOfDay);

        return exerciseTimes.stream()
            .map(ExerciseTime::getExerciseTime)
            .reduce(Duration.ZERO, Duration::plus);
    }

}
