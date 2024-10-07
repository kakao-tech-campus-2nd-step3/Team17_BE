package homeTry.exerciseList.service;

import homeTry.exerciseList.exception.badRequestException.ExerciseDeprecatedException;
import homeTry.exerciseList.exception.badRequestException.ExerciseNotFoundException;
import homeTry.exerciseList.exception.badRequestException.ExerciseAlreadyStartedException;
import homeTry.exerciseList.exception.badRequestException.ExerciseNotStartedException;
import homeTry.exerciseList.exception.badRequestException.NoExercisePermissionException;
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
    }

    @Transactional
    public void startExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseByIdAndMember(exerciseId, memberDTO);

        // 삭제한 운동을 시작하려는 경우
        if (exercise.isDeprecated()) {
            throw new ExerciseDeprecatedException();
        }

        // 실행 중인 운동이 있는지
        long activeExerciseCount = exerciseRepository.countActiveExercisesByMemberId(
            memberDTO.id());
        if (activeExerciseCount > 0) {
            throw new ExerciseAlreadyStartedException();
        }

        // 현재 운동의 상태 확인
        ExerciseTime currentExerciseTime = exerciseTimeService.getExerciseTime(
            exercise.getExerciseId());

        // 처음 운동을 시작한다면, 새로 생성
        if (currentExerciseTime == null) {
            currentExerciseTime = new ExerciseTime(exercise);
        }

        currentExerciseTime.startExercise();
        exerciseTimeService.saveExerciseTime(currentExerciseTime);
    }

    @Transactional
    public void stopExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseByIdAndMember(exerciseId, memberDTO);
        ExerciseTime currentExerciseTime = exerciseTimeService.getExerciseTime(
            exercise.getExerciseId());

        if (currentExerciseTime == null || !currentExerciseTime.isActive()) {
            throw new ExerciseNotStartedException();
        }

        // 하루 최대 12시간, 한 번에 저장되는 최대 시간 8시간을 넘었는지 확인
        exerciseTimeService.validateExerciseDurationLimits(currentExerciseTime);

        currentExerciseTime.stopExercise();
    }

    private Exercise getExerciseByIdAndMember(Long exerciseId, MemberDTO memberDTO) {
        return exerciseRepository.findByIdAndMemberId(exerciseId, memberDTO.id())
            .orElseThrow(ExerciseNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Exercise> findAllExercises() {
        return exerciseRepository.findAll();
    }

}
