package homeTry.exerciseList.controller;

import homeTry.common.annotation.LoginMember;
import homeTry.exerciseList.dto.request.ExerciseRequest;
import homeTry.exerciseList.service.ExerciseService;
import homeTry.member.dto.MemberDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<Void> createExercise(@Valid @RequestBody ExerciseRequest request,
                                               @LoginMember MemberDTO memberDTO) {

        exerciseService.createExercise(request, memberDTO);  // 운동 생성
        return new ResponseEntity<>(HttpStatus.CREATED);  // 상태 코드 201
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long exerciseId,
                                               @LoginMember MemberDTO memberDTO) {

        exerciseService.deleteExercise(exerciseId, memberDTO);  // 운동 삭제
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 상태 코드 204
    }

    @PostMapping("/{exerciseId}")
    public ResponseEntity<Void> startExercise(@PathVariable Long exerciseId,
                                              @LoginMember MemberDTO memberDTO) {

        exerciseService.startExercise(exerciseId, memberDTO);  // 운동 시작
        return new ResponseEntity<>(HttpStatus.OK);  // 상태 코드 200
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<Void> stopExercise(@PathVariable Long exerciseId,
                                             @LoginMember MemberDTO memberDTO) {

        exerciseService.stopExercise(exerciseId, memberDTO);  // 운동 종료
        return new ResponseEntity<>(HttpStatus.OK);  // 상태 코드 200
    }

}
