package homeTry.exerciseList.service;

import homeTry.exerciseList.repository.ExerciseRepository;
import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.dto.ExerciseRequest;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
import homeTry.member.repository.MemberRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final MemberRepository memberRepository;

    public ExerciseService(ExerciseRepository exerciseRepository,
        MemberRepository memberRepository) {
        this.exerciseRepository = exerciseRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createExercise(ExerciseRequest request, MemberDTO memberDTO) {

        Member member = memberRepository.findByEmail(new Email(memberDTO.email()))
            .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 사용자를 찾을 수 없습니다."));

        Exercise exercise = new Exercise(request.exerciseName(), member);
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void deleteExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseListById(exerciseId);
        exercise.markAsDeprecated(); // isDeprecated 값을 true로 설정
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void startExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseListById(exerciseId);
        exercise.startExercise();
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void stopExercise(Long exerciseId, MemberDTO memberDTO) {
        Exercise exercise = getExerciseListById(exerciseId);
        exercise.stopExercise();
        exerciseRepository.save(exercise);
    }

    private Exercise getExerciseListById(Long exerciseId) {
        return exerciseRepository.findById(exerciseId)
            .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    }

    @Transactional(readOnly = true)
    public Duration getWeeklyTotalExercise(String memberEmail) {
        // 이번 주의 시작과 끝 계산
        LocalDateTime startOfWeek = LocalDateTime.now()
            .minusDays(LocalDateTime.now().getDayOfWeek().getValue() - 1) // 그 주 월요일로
            .toLocalDate()
            .atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        List<Exercise> weeklyExercises = exerciseRepository.findByStartTimeBetween(
            startOfWeek, endOfWeek);

        return sumExerciseTime(weeklyExercises);
    }

    @Transactional(readOnly = true)
    public Duration getMonthlyTotalExercise(String memberEmail) {
        // 이번 달의 시작과 끝 계산
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusDays(1)
            .withHour(23).withMinute(59).withSecond(59);

        List<Exercise> monthlyExercises = exerciseRepository.findByStartTimeBetween(
            startOfMonth, endOfMonth);

        return sumExerciseTime(monthlyExercises);
    }

    private Duration sumExerciseTime(List<Exercise> exercises) {
        return exercises.stream()
            .map(Exercise::getExerciseTime)
            .reduce(Duration.ZERO, Duration::plus);
    }

}
