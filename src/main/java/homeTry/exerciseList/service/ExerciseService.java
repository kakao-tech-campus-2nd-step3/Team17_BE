package homeTry.exerciseList.service;

import homeTry.exerciseList.exception.ExerciseNotFoundException;
import homeTry.exerciseList.exception.ExerciseAlreadyStartedException;
import homeTry.exerciseList.exception.ExerciseNotStartedException;
import homeTry.exerciseList.exception.NoExercisePermissionException;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.repository.ExerciseHistoryRepository;
import homeTry.exerciseList.repository.ExerciseRepository;
import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.dto.ExerciseRequest;
import homeTry.exerciseList.repository.ExerciseTimeRepository;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.service.MemberService;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseHistoryRepository exerciseHistoryRepository;
    private final ExerciseTimeRepository exerciseTimeRepository;
    private final MemberService memberService;

    public ExerciseService(ExerciseRepository exerciseRepository,
        ExerciseHistoryRepository exerciseHistoryRepository,
        ExerciseTimeRepository exerciseTimeRepository, MemberService memberService) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseHistoryRepository = exerciseHistoryRepository;
        this.exerciseTimeRepository = exerciseTimeRepository;
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
        if (exercise.getCurrentExerciseTime().getStartTime() != null) {
            throw new ExerciseAlreadyStartedException();
        }
        exercise.startExercise();
        exerciseTimeRepository.save(exercise.getCurrentExerciseTime());
    }

    @Transactional
    public void stopExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseByIdAndMember(exerciseId, memberDTO);
        if (exercise.getCurrentExerciseTime().getStartTime() == null) {
            throw new ExerciseNotStartedException();
        }
        exercise.stopExercise();
        exerciseTimeRepository.save(exercise.getCurrentExerciseTime());
    }

    private Exercise getExerciseByIdAndMember(Long exerciseId, MemberDTO memberDTO) {
        MemberDTO foundMember = memberService.getMember(memberDTO.id());
        return exerciseRepository.findByIdAndMemberId(exerciseId, foundMember.id())
            .orElseThrow(ExerciseNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Duration getWeeklyTotalExercise(Long memberId) {
        // 이번 주의 시작과 끝 계산 (새벽 3시 기준), 하루 시작: 새벽 3시, 하루 끝: 다음날 새벽 2시 59분 59초
        LocalDate startOfWeek = LocalDate.now()
            .minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        LocalDateTime startOfWeekWith3AM = startOfWeek.atTime(3, 0, 0);
        LocalDateTime endOfWeekWith3AM = startOfWeek.plusDays(6).atTime(2, 59, 59);

        List<ExerciseHistory> weeklyExercises = exerciseHistoryRepository.findByExerciseMemberIdAndCreatedAtBetween(
            memberId, startOfWeekWith3AM, endOfWeekWith3AM);

        return sumExerciseTime(weeklyExercises);
    }

    @Transactional(readOnly = true)
    public Duration getMonthlyTotalExercise(Long memberId) {
        // 이번 달의 시작과 끝 계산
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDateTime startOfMonthWith3AM = startOfMonth.atTime(3, 0, 0);
        LocalDateTime endOfMonthWith3AM = startOfMonth.plusMonths(1).minusDays(1).atTime(2, 59, 59);

        List<ExerciseHistory> monthlyExercises = exerciseHistoryRepository.findByExerciseMemberIdAndCreatedAtBetween(
            memberId, startOfMonthWith3AM, endOfMonthWith3AM);

        return sumExerciseTime(monthlyExercises);
    }

    private Duration sumExerciseTime(List<ExerciseHistory> exercises) {
        return exercises.stream()
            .map(ExerciseHistory::getExerciseHistoryTime)
            .reduce(Duration.ZERO, Duration::plus);
    }

}
