package homeTry.exerciseList.service;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseHistoryRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseHistoryService {

    private final ExerciseHistoryRepository exerciseHistoryRepository;

    public ExerciseHistoryService(ExerciseHistoryRepository exerciseHistoryRepository) {
        this.exerciseHistoryRepository = exerciseHistoryRepository;
    }

    @Transactional
    public void saveExerciseHistory(Exercise exercise, ExerciseTime exerciseTime) {
        if (!exerciseTime.getExerciseTime().isZero()) {
            ExerciseHistory history = new ExerciseHistory(exercise, exerciseTime.getExerciseTime());
            exerciseHistoryRepository.save(history);
        }
    }

    // 특정 날에 대한 운동 시간 반환
    @Transactional(readOnly = true)
    public Duration getExerciseHistoriesForDay(Long memberId, LocalDate date) {
        LocalDateTime startOfDay = date.atTime(3, 0, 0);
        LocalDateTime endOfDay = date.plusDays(1).atTime(2, 59, 59);

        List<ExerciseHistory> exercises = exerciseHistoryRepository.findByExerciseMemberIdAndCreatedAtBetween(
            memberId, startOfDay, endOfDay);

        return sumExerciseTime(exercises);
    }

    @Transactional(readOnly = true)
    public Duration getWeeklyTotalExercise(Long memberId) {
        // 이번 주의 시작과 끝 계산 (새벽 3시 기준), 하루 시작: 새벽 3시, 하루 끝: 다음날 새벽 2시 59분 59초
        LocalDate startOfWeek = LocalDate.now()
            .minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        LocalDateTime startOfWeekWith3AM = startOfWeek.atTime(3, 0, 0);
        LocalDateTime endOfWeekWith3AM = startOfWeek.plusDays(6).atTime(2, 59, 59);

        List<ExerciseHistory> weeklyExercises = exerciseHistoryRepository.findByExerciseMemberIdAndCreatedAtBetween(
            memberId, startOfWeekWith3AM, endOfWeekWith3AM);

        return sumExerciseTime(weeklyExercises);
    }

    @Transactional(readOnly = true)
    public Duration getMonthlyTotalExercise(Long memberId) {
        // 이번 달의 시작과 끝 계산
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDateTime startOfMonthWith3AM = startOfMonth.atTime(3, 0, 0);
        LocalDateTime endOfMonthWith3AM = startOfMonth.plusMonths(1).minusDays(1).atTime(2, 59, 59);

        List<ExerciseHistory> monthlyExercises = exerciseHistoryRepository.findByExerciseMemberIdAndCreatedAtBetween(
            memberId, startOfMonthWith3AM, endOfMonthWith3AM);

        return sumExerciseTime(monthlyExercises);
    }

    private Duration sumExerciseTime(List<ExerciseHistory> exercises) {
        return exercises.stream()
            .map(ExerciseHistory::getExerciseHistoryTime)
            .reduce(Duration.ZERO, Duration::plus);
    }
}
