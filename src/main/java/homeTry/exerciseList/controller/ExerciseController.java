package homeTry.exerciseList.controller;

import homeTry.common.annotation.LoginMember;
import homeTry.exerciseList.dto.request.ExerciseRequest;
import homeTry.exerciseList.service.ExerciseService;
import homeTry.member.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Exercise", description = "운동 관련 API")
@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    @Operation(summary = "운동 생성", description = "새로운 운동 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "운동이 성공적으로 생성됨")
    })
    public ResponseEntity<Void> createExercise(@Valid @RequestBody ExerciseRequest request,
        @LoginMember MemberDTO memberDTO) {

        exerciseService.createExercise(request, memberDTO);  // 운동 생성
        return new ResponseEntity<>(HttpStatus.CREATED);  // 상태 코드 201
    }

    @DeleteMapping("/{exerciseId}")
    @Operation(summary = "운동 삭제", description = "특정 운동 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "운동이 성공적으로 삭제됨")
    })
    public ResponseEntity<Void> deleteExercise(@PathVariable Long exerciseId,
        @LoginMember MemberDTO memberDTO) {

        exerciseService.deleteExercise(exerciseId, memberDTO);  // 운동 삭제
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 상태 코드 204
    }

    @PostMapping("/{exerciseId}")
    @Operation(summary = "운동 시작", description = "특정 운동 시작")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "운동이 성공적으로 시작됨")
    })
    public ResponseEntity<Void> startExercise(@PathVariable("exerciseId") Long exerciseId,
        @LoginMember MemberDTO memberDTO) {

        exerciseService.startExercise(exerciseId, memberDTO);  // 운동 시작
        return new ResponseEntity<>(HttpStatus.OK);  // 상태 코드 200
    }

    @PutMapping("/{exerciseId}")
    @Operation(summary = "운동 종료", description = "특정 운동 종료")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "운동이 성공적으로 종료됨")
    })
    public ResponseEntity<Void> stopExercise(@PathVariable Long exerciseId,
        @LoginMember MemberDTO memberDTO) {

        exerciseService.stopExercise(exerciseId, memberDTO);  // 운동 종료
        return new ResponseEntity<>(HttpStatus.OK);  // 상태 코드 200
    }

}
