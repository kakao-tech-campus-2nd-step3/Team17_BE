package homeTry.exerciseList.service;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseHistoryRepository;
import homeTry.exerciseList.repository.ExerciseRepository;
import homeTry.exerciseList.repository.ExerciseTimeRepository;
import java.time.Duration;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseSchedulerService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseHistoryRepository exerciseHistoryRepository;
    private final ExerciseTimeRepository exerciseTimeRepository;

    public ExerciseSchedulerService(ExerciseRepository exerciseRepository,
        ExerciseHistoryRepository exerciseHistoryRepository,
        ExerciseTimeRepository exerciseTimeRepository) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseHistoryRepository = exerciseHistoryRepository;
        this.exerciseTimeRepository = exerciseTimeRepository;
    }

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void saveAllExerciseHistoryAt3AM() {
        List<Exercise> allExercises = exerciseRepository.findAll();

        // 모든 운동 기록을 히스토리에 저장하고 운동 시간을 초기화
        for (Exercise exercise : allExercises) {
            saveExerciseHistory(exercise);
            resetExerciseTime(exercise.getCurrentExerciseTime());
        }
    }

    private void saveExerciseHistory(Exercise exercise) {
        // 운동 시간이 0이 아니면 ExerciseHistory에 저장
        Duration totalExerciseTime = exercise.calculateDuration();
        if (!totalExerciseTime.isZero()) {
            ExerciseHistory history = new ExerciseHistory(exercise, totalExerciseTime);
            exerciseHistoryRepository.save(history);
        }
    }

    private void resetExerciseTime(ExerciseTime exerciseTime) {
        // exercise_time을 0으로 초기화
        exerciseTime.resetExerciseTime();
        exerciseTimeRepository.save(exerciseTime);
    }

}
