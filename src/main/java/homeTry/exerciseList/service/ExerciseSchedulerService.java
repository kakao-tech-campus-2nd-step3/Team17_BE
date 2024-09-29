package homeTry.exerciseList.service;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseTime;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseSchedulerService {

    private final ExerciseService exerciseService;
    private final ExerciseTimeService exerciseTimeService;
    private final ExerciseHistoryService exerciseHistoryService;

    public ExerciseSchedulerService(ExerciseService exerciseService,
        ExerciseTimeService exerciseTimeService,
        ExerciseHistoryService exerciseHistoryService) {
        this.exerciseService = exerciseService;
        this.exerciseTimeService = exerciseTimeService;
        this.exerciseHistoryService = exerciseHistoryService;
    }

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void saveAllExerciseHistoryAt3AM() {
        List<Exercise> allExercises = exerciseService.findAllExercises();

        // 모든 운동 기록을 히스토리에 저장하고 운동 시간을 초기화
        for (Exercise exercise : allExercises) {
            ExerciseTime exerciseTime = exercise.getCurrentExerciseTime();
            exerciseHistoryService.saveExerciseHistory(exercise, exerciseTime);
            exerciseTimeService.resetExerciseTime(exerciseTime);
        }
    }

}
