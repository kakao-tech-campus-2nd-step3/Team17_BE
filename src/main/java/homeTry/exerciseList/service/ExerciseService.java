package homeTry.exerciseList.service;

import homeTry.exerciseList.exception.badRequestException.AnotherExerciseInProgressException;
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
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void startExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseByIdAndMember(exerciseId, memberDTO);

        // 삭제한 운동을 시작하려는 경우
        if (exercise.isDeprecated()) {
            throw new ExerciseDeprecatedException();
        }

        // 하루 총 운동 시간이 12시간을 초과했는지 확인
        exerciseTimeService.validateStartExercise(memberDTO.id());

        // 현재 운동의 상태 확인
        ExerciseTime currentExerciseTime = exerciseTimeService.getExerciseTime(exercise.getExerciseId());

        if (currentExerciseTime != null && currentExerciseTime.isActive()) {
            throw new ExerciseAlreadyStartedException(); // 이미 시작된 운동을 종료 전 다시 시작하려는 경우
        }

        // 실행 중이 운동이 있는지
        List<Exercise> activeExercises = exerciseRepository.findAllByMemberId(memberDTO.id()).stream()
            .filter(ex -> exerciseTimeService.isExerciseActive(ex.getExerciseId()))
            .toList();

        if (!activeExercises.isEmpty()) {
            throw new AnotherExerciseInProgressException(); // 운동이 진행 중인 경우
        }

        if (currentExerciseTime == null) {
            currentExerciseTime = new ExerciseTime(exercise); // 처음 운동을 시작한다면, 새로 생성
        }

        currentExerciseTime.startExercise();
        exerciseTimeService.saveExerciseTime(currentExerciseTime);
    }

    @Transactional
    public void stopExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseByIdAndMember(exerciseId, memberDTO);
        ExerciseTime currentExerciseTime = exerciseTimeService.getExerciseTime(exercise.getExerciseId());

        if (!currentExerciseTime.isActive()) {
            throw new ExerciseNotStartedException();
        }

        // 하루 최대 12시간, 한 번에 저장되는 최대 시간 8시간을 넘었는지 확인
        exerciseTimeService.validateDailyExerciseLimit(currentExerciseTime);

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

}
