package homeTry.exerciseList.service;

import homeTry.exerciseList.repository.ExerciseListRepository;
import homeTry.exerciseList.model.entity.ExerciseList;
import homeTry.exerciseList.dto.ExerciseListRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseListService {

    private final ExerciseListRepository exerciseListRepository;

    public ExerciseListService(ExerciseListRepository exerciseListRepository) {
        this.exerciseListRepository = exerciseListRepository;
    }

    @Transactional
    public void createExercise(ExerciseListRequest request) {
        ExerciseList exerciseList = new ExerciseList(request.exerciseName());
        exerciseListRepository.save(exerciseList);
    }

    @Transactional
    public void deleteExercise(Long exerciseId) {
        ExerciseList exerciseList = getExerciseListById(exerciseId);
        exerciseList.markAsDeprecated(); // isDeprecated 값을 true로 설정
        exerciseListRepository.save(exerciseList);
    }

    @Transactional
    public void startExercise(Long exerciseId) {
        ExerciseList exerciseList = getExerciseListById(exerciseId);
        exerciseList.startExercise();
        exerciseListRepository.save(exerciseList);
    }

    @Transactional
    public void stopExercise(Long exerciseId) {
        ExerciseList exerciseList = getExerciseListById(exerciseId);
        exerciseList.stopExercise();
        exerciseListRepository.save(exerciseList);
    }

    private ExerciseList getExerciseListById(Long exerciseId) {
        return exerciseListRepository.findById(exerciseId)
            .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    }

    @Transactional
    public Duration getWeeklyTotalExercise(String memberEmail) {
        // 이번 주의 시작과 끝 계산
        LocalDateTime startOfWeek = LocalDateTime.now()
            .minusDays(LocalDateTime.now().getDayOfWeek().getValue() - 1) // 그 주 월요일로
            .toLocalDate()
            .atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        List<ExerciseList> weeklyExercises = exerciseListRepository.findByDateTimeBetween(
            startOfWeek, endOfWeek);

        return sumExerciseTime(weeklyExercises);
    }

    public Duration getMonthlyTotalExercise(String memberEmail) {
        // 이번 달의 시작과 끝 계산
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusDays(1)
            .withHour(23).withMinute(59).withSecond(59);

        List<ExerciseList> monthlyExercises = exerciseListRepository.findByDateTimeBetween(
            startOfMonth, endOfMonth);

        return sumExerciseTime(monthlyExercises);
    }

    private Duration sumExerciseTime(List<ExerciseList> exercises) {
        return exercises.stream()
            .map(ExerciseList::getExerciseTime)
            .reduce(Duration.ZERO, Duration::plus);
    }

}
