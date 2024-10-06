package homeTry.exerciseList.service;

import homeTry.constants.DateTimeUtil;
import homeTry.exerciseList.dto.ExerciseResponse;
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

    // 운동 저장
    @Transactional
    public void saveExerciseHistory(Exercise exercise, ExerciseTime exerciseTime) {
        if (exerciseTime != null && !exerciseTime.getExerciseTime().isZero()) {
            ExerciseHistory history = new ExerciseHistory(exercise, exerciseTime.getExerciseTime());
            exerciseHistoryRepository.save(history);
        }
    }

    // 특정 날에 대한 운동 전체 시간 반환
    @Transactional(readOnly = true)
    public Duration getExerciseHistoriesForDay(Long memberId, LocalDate date) {
        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(date);
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(date);

        List<ExerciseHistory> exercises = exerciseHistoryRepository.findByExerciseMemberIdAndCreatedAtBetween(
            memberId, startOfDay, endOfDay);

        return sumExerciseTime(exercises);
    }

    // 이번 주의 운동 전체 시간 반환 - 마이페이지 조회 시 사용
    @Transactional(readOnly = true)
    public Duration getWeeklyTotalExercise(Long memberId) {
        // 이번 주의 시작과 끝 계산 (새벽 3시 기준), 하루 시작: 새벽 3시, 하루 끝: 다음날 새벽 2시 59분 59초
        LocalDateTime startOfWeekWith3AM = DateTimeUtil.getStartOfWeek(LocalDate.now());
        LocalDateTime endOfWeekWith3AM = DateTimeUtil.getEndOfWeek(LocalDate.now());

        List<ExerciseHistory> weeklyExercises = exerciseHistoryRepository.findByExerciseMemberIdAndCreatedAtBetween(
            memberId, startOfWeekWith3AM, endOfWeekWith3AM);

        return sumExerciseTime(weeklyExercises);
    }

    // 이번 달의 운동 전체 시간 반환 - 마이페이지 조회 시 사용
    @Transactional(readOnly = true)
    public Duration getMonthlyTotalExercise(Long memberId) {
        // 이번 달의 시작과 끝 계산
        LocalDateTime startOfMonthWith3AM = DateTimeUtil.getStartOfMonth(LocalDate.now());
        LocalDateTime endOfMonthWith3AM = DateTimeUtil.getEndOfMonth(LocalDate.now());

        List<ExerciseHistory> monthlyExercises = exerciseHistoryRepository.findByExerciseMemberIdAndCreatedAtBetween(
            memberId, startOfMonthWith3AM, endOfMonthWith3AM);

        return sumExerciseTime(monthlyExercises);
    }

    // 운동 시간 합
    private Duration sumExerciseTime(List<ExerciseHistory> exercises) {
        return exercises.stream()
            .map(ExerciseHistory::getExerciseHistoryTime)
            .reduce(Duration.ZERO, Duration::plus);
    }

    // 메인페이지 조회 시 사용
    // 특정 날에 대한 운동 기록 반환
    @Transactional(readOnly = true)
    public List<ExerciseResponse> getExerciseResponsesForDay(Long memberId, LocalDate date) {
        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(date);
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(date);

        List<ExerciseHistory> exerciseHistories = exerciseHistoryRepository.findByExerciseMemberIdAndCreatedAtBetween(
            memberId, startOfDay, endOfDay);

        return exerciseHistories.stream()
            .map(ExerciseResponse::from)
            .toList();
    }
}
