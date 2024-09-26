package homeTry.exerciseList.service;

import homeTry.exerciseList.repository.ExerciseListRepository;
import homeTry.exerciseList.model.entity.ExerciseList;
import homeTry.exerciseList.dto.ExerciseListRequest;
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
public class ExerciseListService {

    private final ExerciseListRepository exerciseListRepository;
    private final MemberRepository memberRepository;

    public ExerciseListService(ExerciseListRepository exerciseListRepository,
        MemberRepository memberRepository) {
        this.exerciseListRepository = exerciseListRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createExercise(ExerciseListRequest request, MemberDTO memberDTO) {

        Member member = memberRepository.findByEmail(new Email(memberDTO.email()))
            .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 사용자를 찾을 수 없습니다."));

        ExerciseList exerciseList = new ExerciseList(request.exerciseName(), member);
        exerciseListRepository.save(exerciseList);
    }

    @Transactional
    public void deleteExercise(Long exerciseId, MemberDTO memberDTO) {
        ExerciseList exerciseList = getExerciseListById(exerciseId);
        exerciseList.markAsDeprecated(); // isDeprecated 값을 true로 설정
        exerciseListRepository.save(exerciseList);
    }

    @Transactional
    public void startExercise(Long exerciseId, MemberDTO memberDTO) {
        ExerciseList exerciseList = getExerciseListById(exerciseId);
        exerciseList.startExercise();
        exerciseListRepository.save(exerciseList);
    }

    @Transactional
    public void stopExercise(Long exerciseId, MemberDTO memberDTO) {
        ExerciseList exerciseList = getExerciseListById(exerciseId);
        exerciseList.stopExercise();
        exerciseListRepository.save(exerciseList);
    }

    private ExerciseList getExerciseListById(Long exerciseId) {
        return exerciseListRepository.findById(exerciseId)
            .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    }

    @Transactional
    public Duration getWeeklyTotalExercise(String memberEmail) {
        // 이번 주의 시작과 끝 계산
        LocalDateTime startOfWeek = LocalDateTime.now()
            .minusDays(LocalDateTime.now().getDayOfWeek().getValue() - 1) // 그 주 월요일로
            .toLocalDate()
            .atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        List<ExerciseList> weeklyExercises = exerciseListRepository.findByStartTimeBetween(
            startOfWeek, endOfWeek);

        return sumExerciseTime(weeklyExercises);
    }

    public Duration getMonthlyTotalExercise(String memberEmail) {
        // 이번 달의 시작과 끝 계산
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusDays(1)
            .withHour(23).withMinute(59).withSecond(59);

        List<ExerciseList> monthlyExercises = exerciseListRepository.findByStartTimeBetween(
            startOfMonth, endOfMonth);

        return sumExerciseTime(monthlyExercises);
    }

    private Duration sumExerciseTime(List<ExerciseList> exercises) {
        return exercises.stream()
            .map(ExerciseList::getExerciseTime)
            .reduce(Duration.ZERO, Duration::plus);
    }

}
