package homeTry.member.dto;

import homeTry.exerciseList.model.entity.Exercise;
import java.util.List;

public record GetExerciseListDTO(List<Exercise>exerciseList) { }
