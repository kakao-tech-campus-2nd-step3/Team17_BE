package homeTry.diary.model.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public class ExerciseRecord {

     private final String exerciseRecord;

    public ExerciseRecord(String exerciseRecord) {
        //constraints
        this.exerciseRecord = exerciseRecord;
    }

    @JsonValue
    public String getExerciseRecord() {
        return exerciseRecord;
    }
    
}
