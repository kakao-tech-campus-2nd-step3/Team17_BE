package homeTry.exerciseList.event;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.service.ExerciseTimeHelper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ExerciseCreationEventListener {

    private final ExerciseTimeHelper exerciseTimeHelper;

    public ExerciseCreationEventListener(ExerciseTimeHelper exerciseTimeHelper) {
        this.exerciseTimeHelper = exerciseTimeHelper;
    }

    // "ExerciseCreationEvent"를 수신하고 처리
    // Exercise 생성 이벤트 발생 -> ExerciseTime 생성 및 저장
    @EventListener
    public void handleExerciseCreationEvent(ExerciseCreationEvent event) {
        Exercise exercise = event.exercise();

        ExerciseTime currentExerciseTime = new ExerciseTime(exercise);
        exerciseTimeHelper.saveExerciseTime(currentExerciseTime);
    }

}
