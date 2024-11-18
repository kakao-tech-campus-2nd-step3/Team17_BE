package homeTry.exerciseList.service;

import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseTimeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExerciseTimeHelper {

    private final ExerciseTimeRepository exerciseTimeRepository;

    public ExerciseTimeHelper(ExerciseTimeRepository exerciseTimeRepository) {
        this.exerciseTimeRepository = exerciseTimeRepository;
    }

    @Transactional
    public void saveExerciseTime(ExerciseTime exerciseTime) {
        exerciseTimeRepository.save(exerciseTime);
    }

}
