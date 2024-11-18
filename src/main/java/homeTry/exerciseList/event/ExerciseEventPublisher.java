package homeTry.exerciseList.event;

import homeTry.exerciseList.model.entity.Exercise;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

// Exercise 생성 이벤트 발행
@Component
public class ExerciseEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ExerciseEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // ExerciseCreationEvent 생성 후 이벤트 발행
    public void publishCreationEvent(Exercise exercise) {
        ExerciseCreationEvent event = new ExerciseCreationEvent(exercise);
        applicationEventPublisher.publishEvent(event);
    }

}
