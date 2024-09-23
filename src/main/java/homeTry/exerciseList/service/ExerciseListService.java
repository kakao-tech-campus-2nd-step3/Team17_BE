package homeTry.exerciseList.service;

import homeTry.exerciseList.repository.ExerciseListRepository;
import homeTry.exerciseList.model.entity.ExerciseList;
import homeTry.exerciseList.dto.ExerciseListRequest;
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
        ExerciseList exerciseList = validateExerciseExists(exerciseId);
        exerciseList.markAsDeprecated(); // isDeprecated 값을 true로 설정
        exerciseListRepository.save(exerciseList);
    }

    @Transactional
    public void startExercise(Long exerciseId) {
        ExerciseList exerciseList = validateExerciseExists(exerciseId);
        exerciseList.startExercise();
    }

    @Transactional
    public void stopExercise(Long exerciseId) {
        ExerciseList exerciseList = validateExerciseExists(exerciseId);
        exerciseList.stopExercise();
        exerciseListRepository.save(exerciseList);
    }

    private ExerciseList validateExerciseExists(Long exerciseId) {
        return exerciseListRepository.findById(exerciseId)
            .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    }

}
