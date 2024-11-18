package homeTry.exerciseList.service;

import homeTry.exerciseList.dto.request.ExerciseRequest;
import homeTry.exerciseList.event.ExerciseEventPublisher;
import homeTry.exerciseList.exception.badRequestException.*;
import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseRepository;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseEventPublisher exerciseEventPublisher;
    private final ExerciseTimeService exerciseTimeService;
    private final ExerciseHistoryService exerciseHistoryService;
    private final MemberService memberService;

    public ExerciseService(ExerciseRepository exerciseRepository,
        ExerciseEventPublisher exerciseEventPublisher,
        ExerciseTimeService exerciseTimeService, ExerciseHistoryService exerciseHistoryService,
        MemberService memberService) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseEventPublisher = exerciseEventPublisher;
        this.exerciseTimeService = exerciseTimeService;
        this.exerciseHistoryService = exerciseHistoryService;
        this.memberService = memberService;
    }

    @Transactional
    public void createExercise(ExerciseRequest request, MemberDTO memberDTO) {
        Member foundMember = memberService.getMemberEntity(memberDTO.id());

        Exercise exercise = new Exercise(request.exerciseName(), foundMember);
        exerciseRepository.save(exercise);

        exerciseEventPublisher.publishCreationEvent(exercise);
    }

    @Transactional
    public void deleteExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = findExerciseWithPermissionCheck(exerciseId, memberDTO);

        ExerciseTime currentExerciseTime = exerciseTimeService.getExerciseTime(
            exercise.getExerciseId());
        if (currentExerciseTime != null && currentExerciseTime.isActive()) {
            throw new ExerciseInProgressException();
        }

        exercise.markAsDeprecated(); // isDeprecated 값을 true로 설정
    }

    @Transactional
    public void startExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = findExerciseWithPermissionCheck(exerciseId, memberDTO);

        // 삭제한 운동을 시작하려는 경우
        if (exercise.isDeprecated()) {
            throw new ExerciseDeprecatedException();
        }

        exerciseTimeService.startExerciseTime(exercise);
    }

    @Transactional
    public void stopExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = findExerciseWithPermissionCheck(exerciseId, memberDTO);
        exerciseTimeService.stopExerciseTime(exercise);
    }

    private Exercise findExerciseWithPermissionCheck(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseById(exerciseId);

        // 해당 운동이 해당 회원의 것인지 검증
        if (!exercise.getMember().getId().equals(memberDTO.id())) {
            throw new NoExercisePermissionException();
        }

        return exercise;
    }

    private Exercise getExerciseById(Long exerciseId) {
        return exerciseRepository.findById(exerciseId)
            .orElseThrow(ExerciseNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Exercise> findAllNonDeprecatedExercises() {
        return exerciseRepository.findByIsDeprecatedFalse();
    }

    @Transactional
    public void deleteAllExercisesByMemberId(Long memberId) {
        // 관련된 ExerciseTime, ExerciseHistory 삭제
        List<Exercise> exercises = exerciseRepository.findByMemberId(memberId);
        for (Exercise exercise : exercises) {
            exerciseTimeService.deleteExerciseTimesByExerciseId(exercise.getExerciseId());
            exerciseHistoryService.deleteExerciseHistoriesByExerciseId(exercise.getExerciseId());
        }

        // Exercise 삭제
        exerciseRepository.deleteByMemberId(memberId);
    }

}
