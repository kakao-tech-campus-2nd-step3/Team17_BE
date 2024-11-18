package homeTry.exerciseList.service;

import homeTry.common.constants.DateTimeUtil;
import homeTry.exerciseList.dto.response.ExerciseResponse;
import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExerciseHistoryService {

    private final ExerciseHistoryRepository exerciseHistoryRepository;

    public ExerciseHistoryService(ExerciseHistoryRepository exerciseHistoryRepository) {
        this.exerciseHistoryRepository = exerciseHistoryRepository;
    }

    // 운동 저장
    @Transactional
    public void saveExerciseHistory(Exercise exercise, ExerciseTime exerciseTime) {
        ExerciseHistory history = new ExerciseHistory(exercise, exerciseTime.getExerciseTime(),
            exerciseTime.getStartTime());
        exerciseHistoryRepository.save(history);
    }

    // 메인페이지, 팀 랭킹
    // 특정 날에 대한 운동 전체 시간 반환
    @Transactional(readOnly = true)
    public Long getExerciseHistoriesForDay(Long memberId, LocalDate date) {
        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(date);
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(date);

        List<ExerciseHistory> exercises = exerciseHistoryRepository.findExerciseHistoriesForMemberOnDate(
            memberId, startOfDay, endOfDay);

        return sumExerciseTime(exercises);
    }

    // 이번 주의 운동 전체 시간 반환 - 마이페이지 조회 시 사용
    @Transactional(readOnly = true)
    public Long getWeeklyTotalExercise(Long memberId) {
        // 이번 주의 시작과 끝 계산 (새벽 3시 기준), 하루 시작: 새벽 3시, 하루 끝: 다음날 새벽 2시 59분 59초
        LocalDate adjustedToday = DateTimeUtil.getAdjustedCurrentDate();
        LocalDateTime startOfWeekWith3AM = DateTimeUtil.getStartOfWeek(adjustedToday);
        LocalDateTime endOfWeekWith3AM = DateTimeUtil.getEndOfWeek(adjustedToday);

        List<ExerciseHistory> weeklyExercises = exerciseHistoryRepository.findExerciseHistoriesForMemberOnDate(
            memberId, startOfWeekWith3AM, endOfWeekWith3AM);

        return sumExerciseTime(weeklyExercises);
    }

    // 이번 달의 운동 전체 시간 반환 - 마이페이지 조회 시 사용
    @Transactional(readOnly = true)
    public Long getMonthlyTotalExercise(Long memberId) {
        // 이번 달의 시작과 끝 계산
        LocalDate adjustedToday = DateTimeUtil.getAdjustedCurrentDate();
        LocalDateTime startOfMonthWith3AM = DateTimeUtil.getStartOfMonth(adjustedToday);
        LocalDateTime endOfMonthWith3AM = DateTimeUtil.getEndOfMonth(adjustedToday);

        List<ExerciseHistory> monthlyExercises = exerciseHistoryRepository.findExerciseHistoriesForMemberOnDate(
            memberId, startOfMonthWith3AM, endOfMonthWith3AM);

        return sumExerciseTime(monthlyExercises);
    }

    // 운동 시간 합
    private Long sumExerciseTime(List<ExerciseHistory> exercises) {
        return exercises
            .stream()
            .map(ExerciseHistory::getExerciseHistoryTime)
            .reduce(Duration.ZERO, Duration::plus)
            .toMillis();
    }

    // 메인페이지 조회 시 사용
    // 특정 날에 대한 운동 기록 반환
    @Transactional(readOnly = true)
    public List<ExerciseResponse> getExerciseResponsesForDay(Long memberId, LocalDate date) {
        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(date);
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(date);

        List<ExerciseHistory> exerciseHistories = exerciseHistoryRepository.findExerciseHistoriesForMemberOnDate(
            memberId, startOfDay, endOfDay);

        return exerciseHistories
            .stream()
            .map(ExerciseResponse::from)
            .toList();
    }

    @Transactional
    public void deleteExerciseHistoriesByExerciseId(Long exerciseId) {
        exerciseHistoryRepository.deleteByExerciseId(exerciseId);
    }

}
