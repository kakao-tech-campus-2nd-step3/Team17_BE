package homeTry.exerciseList.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseRepository;
import homeTry.member.model.entity.Member;
import homeTry.member.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
class ExerciseTimeServiceTest {

    @Autowired
    private ExerciseTimeService exerciseTimeService;

    @Autowired
    private ExerciseTimeHelper exerciseTimeHelper;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(new Member("test@example.com", "1234"));
        member = memberRepository.save(member);

        exercise = new Exercise("Test Exercise", member);
        exercise = exerciseRepository.save(exercise);
    }

    @Test
    @DisplayName("8시간 초과 운동에 대해 강제 종료 후 'isActive=false' 로 저장되는지")
    void stopExerciseTime_Fail() {
        // given
        ExerciseTime exerciseTime = new ExerciseTime(exercise);
        exerciseTime.startExercise();
        exerciseTime.startExerciseAt(LocalDateTime.now().minusHours(9));
        exerciseTimeHelper.saveExerciseTime(exerciseTime);

        // when & then
        exerciseTimeService.stopExerciseTime(exercise);

        // 운동 상태 확인
        ExerciseTime updatedExerciseTime = exerciseTimeService.getExerciseTime(exercise.getExerciseId());
        assertThat(updatedExerciseTime.isActive()).isFalse();
        assertThat(updatedExerciseTime.getExerciseTime().toMillis()).isEqualTo(0);
    }

    @Test
    @DisplayName("정상적인 운동 종료")
    void stopExerciseTime_Succeed() {
        // given
        ExerciseTime exerciseTime = new ExerciseTime(exercise);
        exerciseTime.startExercise();
        exerciseTime.startExerciseAt(LocalDateTime.now().minusHours(1));
        exerciseTimeHelper.saveExerciseTime(exerciseTime);

        // when
        exerciseTimeService.stopExerciseTime(exercise);

        // then
        ExerciseTime updatedExerciseTime = exerciseTimeService.getExerciseTime(exercise.getExerciseId());
        assertThat(updatedExerciseTime.isActive()).isFalse();
        assertThat(updatedExerciseTime.getExerciseTime().toHours()).isEqualTo(1);
    }



}