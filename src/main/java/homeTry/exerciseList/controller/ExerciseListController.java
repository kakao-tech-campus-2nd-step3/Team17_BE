package homeTry.exerciseList.controller;

import homeTry.exerciseList.service.ExerciseListService;
import homeTry.exerciseList.dto.ExerciseListRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseListController {

    private final ExerciseListService exerciseListService;

    public ExerciseListController(ExerciseListService exerciseListService) {
        this.exerciseListService = exerciseListService;
    }

    @PostMapping
    public ResponseEntity<Void> createExercise(@RequestBody ExerciseListRequest request,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        exerciseListService.createExercise(request);  // 운동 생성
        return new ResponseEntity<>(HttpStatus.CREATED);  // 상태 코드 201
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long exerciseId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        exerciseListService.deleteExercise(exerciseId);  // 운동 삭제
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 상태 코드 204
    }

    @PutMapping("/{exerciseId}/start")
    public ResponseEntity<Void> startExercise(@PathVariable Long exerciseId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        exerciseListService.startExercise(exerciseId);
        return new ResponseEntity<>(HttpStatus.OK);  // 상태 코드 200
    }

    @PutMapping("/{exerciseId}/stop")
    public ResponseEntity<Void> stopExercise(@PathVariable Long exerciseId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        exerciseListService.stopExercise(exerciseId);
        return new ResponseEntity<>(HttpStatus.OK);  // 상태 코드 200
    }

}
