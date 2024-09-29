package homeTry.exerciseList.service;

import homeTry.exerciseList.dto.ExerciseResponse;
import homeTry.exerciseList.exception.ExerciseNotFoundException;
import homeTry.exerciseList.exception.ExerciseAlreadyStartedException;
import homeTry.exerciseList.exception.ExerciseNotStartedException;
import homeTry.exerciseList.exception.NoExercisePermissionException;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseRepository;
import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.dto.ExerciseRequest;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseTimeService exerciseTimeService;
    private final MemberService memberService;

    public ExerciseService(ExerciseRepository exerciseRepository,
        ExerciseTimeService exerciseTimeService, MemberService memberService) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseTimeService = exerciseTimeService;
        this.memberService = memberService;
    }

    @Transactional
    public void createExercise(ExerciseRequest request, MemberDTO memberDTO) {
        Member foundMember = memberService.getMemberEntity(memberDTO.id());
        Exercise exercise = new Exercise(request.exerciseName(), foundMember);
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void deleteExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseByIdAndMember(exerciseId, memberDTO);

        if (!exercise.getMember().getId().equals(memberDTO.id())) {
            throw new NoExercisePermissionException();
        }

        exercise.markAsDeprecated(); // isDeprecated 값을 true로 설정
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void startExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseByIdAndMember(exerciseId, memberDTO);
        ExerciseTime currentExerciseTime = exercise.getCurrentExerciseTime();

        if (currentExerciseTime.isActive()) {
            throw new ExerciseAlreadyStartedException();
        }

        currentExerciseTime.startExercise();
        exerciseTimeService.saveExerciseTime(currentExerciseTime);
    }

    @Transactional
    public void stopExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseByIdAndMember(exerciseId, memberDTO);
        ExerciseTime currentExerciseTime = exercise.getCurrentExerciseTime();

        if (!currentExerciseTime.isActive()) {
            throw new ExerciseNotStartedException();
        }

        currentExerciseTime.stopExercise();
        exerciseTimeService.saveExerciseTime(currentExerciseTime);
    }

    private Exercise getExerciseByIdAndMember(Long exerciseId, MemberDTO memberDTO) {
        return exerciseRepository.findByIdAndMemberId(exerciseId, memberDTO.id())
            .orElseThrow(ExerciseNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Exercise> findAllExercises() {
        return exerciseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponse> getExercisesForMember(Long memberId) {
        List<Exercise> exercises = exerciseRepository.findAllByMemberId(memberId);

        return exercises.stream()
            .map(ExerciseResponse::fromEntity)
            .toList();
    }

}
