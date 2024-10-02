package homeTry.exerciseList.service;

import homeTry.exerciseList.exception.DailyExerciseTimeLimitExceededException;
import homeTry.exerciseList.exception.ExerciseTimeLimitExceededException;
import homeTry.exerciseList.exception.ExerciseNotFoundException;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseTimeRepository;
import homeTry.member.dto.MemberDTO;
import homeTry.team.dto.ResponseRanking;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    @Transactional(readOnly = true)
    public ExerciseTime getExerciseTime(Long exerciseId) {
        return exerciseTimeRepository.findByExerciseId(exerciseId)
            .orElseThrow(ExerciseNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public boolean isExerciseActive(Long exerciseId) {
        ExerciseTime exerciseTime = getExerciseTime(exerciseId);
        return exerciseTime.isActive();
    }

    @Transactional(readOnly = true)
    public void validateStartExercise(Long memberId) {
        Duration totalExerciseTimeForToday = getExerciseTimesForToday(memberId);

        // 하루 총 운동 시간이 12시간을 초과했는지 확인
        if (totalExerciseTimeForToday.toHours() > 12) {
            throw new DailyExerciseTimeLimitExceededException();  // 12시간 초과 시 예외 발생
        }
    }

    // 운동 시간 초과 여부 확인
    @Transactional(readOnly = true)
    public void validateDailyExerciseLimit(ExerciseTime exerciseTime) {
        Duration timeElapsed = Duration.between(exerciseTime.getStartTime(), LocalDateTime.now());
        Duration totalTime = exerciseTime.getExerciseTime().plus(timeElapsed);

        if (totalTime.toHours() > 12) {
            throw new DailyExerciseTimeLimitExceededException();
        }

        if (timeElapsed.toHours() > 8) {
            throw new ExerciseTimeLimitExceededException();
        }
    }

    // 당일의 운동 시간 반환
    @Transactional(readOnly = true)
    public Duration getExerciseTimesForToday(Long memberId) {
        LocalDateTime startOfDay = LocalDate.now().atTime(3, 0, 0);
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atTime(2, 59, 59);

        // 해당 멤버의 당일 운동 시간 목록 조회
        List<ExerciseTime> exerciseTimes = exerciseTimeRepository.findByExerciseMemberIdAndStartTimeBetween(memberId, startOfDay, endOfDay);

        return exerciseTimes.stream()
            .map(ExerciseTime::getExerciseTime)
            .reduce(Duration.ZERO, Duration::plus);
    }

    @Transactional(readOnly = true)
    public List<ResponseRanking> getTodayMembersRanking(List<MemberDTO> members) {
        return members.stream()
            .map(member -> {
                Duration totalExerciseTime = getExerciseTimesForToday(member.id());
                return new ResponseRanking(member.nickname(), totalExerciseTime);
            })
            .sorted(Comparator.comparing(ResponseRanking::time).reversed())
            .toList();
    }

}
