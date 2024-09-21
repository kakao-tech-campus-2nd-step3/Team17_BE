package homeTry.exerciseList.service;

import homeTry.exerciseList.repository.ExerciseListRepository;
import homeTry.exerciseList.model.vo.ActiveStatus;
import homeTry.exerciseList.model.vo.DeprecatedStatus;
import homeTry.exerciseList.model.entity.ExerciseList;
import homeTry.exerciseList.model.vo.ExerciseName;
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
        ExerciseName exerciseName = request.toExerciseName();
        DeprecatedStatus deprecatedStatus = new DeprecatedStatus(false);
        ActiveStatus activeStatus = new ActiveStatus(false);

        ExerciseList exerciseList = new ExerciseList(exerciseName, deprecatedStatus, activeStatus);
        exerciseListRepository.save(exerciseList);
    }

    @Transactional
    public void deleteExercise(Long exerciseId) {
        ExerciseList exerciseList = validateOwnership(exerciseId);
        exerciseListRepository.delete(exerciseList);
    }

    @Transactional
    public void startExercise(Long exerciseId) {
        ExerciseList exerciseList = validateOwnership(exerciseId);
        exerciseList.startExercise();
        exerciseListRepository.save(exerciseList);
    }

    @Transactional
    public void stopExercise(Long exerciseId) {
        ExerciseList exerciseList = validateOwnership(exerciseId);
        exerciseList.stopExercise();
        exerciseListRepository.save(exerciseList);
    }

    // 멤버 권한 확인 추가될 예정
    private ExerciseList validateOwnership(Long exerciseId) {
        ExerciseList exerciseList = exerciseListRepository.findById(exerciseId)
            .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

        return exerciseList;
    }

}
