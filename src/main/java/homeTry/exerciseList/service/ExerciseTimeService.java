package homeTry.exerciseList.service;

import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseTimeRepository;
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

}
