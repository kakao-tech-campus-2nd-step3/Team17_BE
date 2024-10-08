package homeTry.exerciseList.service;

import homeTry.common.constants.DateTimeUtil;
import homeTry.exerciseList.dto.ExerciseDTO;
import homeTry.exerciseList.exception.badRequestException.DailyExerciseTimeLimitExceededException;
import homeTry.exerciseList.exception.badRequestException.ExerciseTimeLimitExceededException;
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
    }

    @Transactional(readOnly = true)
    public ExerciseTime getExerciseTime(Long exerciseId) {
        return exerciseTimeRepository.findByExerciseId(exerciseId)
            .orElse(null);
    }

    public void validateExerciseDurationLimits(ExerciseTime exerciseTime) {
        Duration timeElapsed = Duration.between(exerciseTime.getStartTime(), LocalDateTime.now());
        Duration totalTime = exerciseTime.getExerciseTime().plus(timeElapsed);

        // 한 번 운동한 시간이 8시간을 초과한 경우
        if (timeElapsed.toHours() > 8) {
            exerciseTime.stopExerciseWithoutSavingTime();  // 기록 저장 없이 강제 종료
            throw new ExerciseTimeLimitExceededException();
        }

        // 하루 총 운동 시간이 12시간을 초과한 경우
        if (totalTime.toHours() > 12) {
            exerciseTime.stopExerciseWithoutSavingTime();
            throw new DailyExerciseTimeLimitExceededException();
        }
    }

    // 당일의 운동 시간 반환
    @Transactional(readOnly = true)
    public Duration getExerciseTimesForToday(Long memberId) {
        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(LocalDate.now());
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(LocalDate.now());

        // 해당 멤버의 당일 운동 시간 목록 조회
        List<ExerciseTime> exerciseTimes = exerciseTimeRepository.findByExerciseMemberIdAndStartTimeBetween(
            memberId, startOfDay, endOfDay);

        // 운동 시간 총 합
        return exerciseTimes.stream()
            .map(ExerciseTime::getExerciseTime)
            .reduce(Duration.ZERO, Duration::plus);
    }

    // 메인 페이지 운동 리스트 반환
    @Transactional(readOnly = true)
    public List<ExerciseDTO> getExerciseResponsesForToday(Long memberId) {
        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(LocalDate.now());
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(LocalDate.now());

        List<ExerciseTime> exerciseTimes = exerciseTimeRepository.findByExerciseMemberIdAndStartTimeBetween(
            memberId, startOfDay, endOfDay);

        return exerciseTimes.stream()
            .map(ExerciseDTO::from)
            .toList();
    }

}
