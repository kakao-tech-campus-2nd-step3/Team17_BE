package homeTry.exerciseList.service;

import static org.junit.jupiter.api.Assertions.*;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseHistoryRepository;
import homeTry.exerciseList.repository.ExerciseRepository;
import homeTry.exerciseList.repository.ExerciseTimeRepository;
import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
import homeTry.member.repository.MemberRepository;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
class ExerciseSchedulerServiceTest {

    @Autowired
    private ExerciseSchedulerService exerciseSchedulerService;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseTimeRepository exerciseTimeRepository;

    @Autowired
    private ExerciseHistoryRepository exerciseHistoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        exerciseHistoryRepository.deleteAll();
        exerciseTimeRepository.deleteAll();
        exerciseRepository.deleteAll();

        if (!memberRepository.existsByEmail(new Email("test@example.com"))) {
            memberRepository.save(new Member("test@example.com", "1234"));
        }
        if (!memberRepository.existsByEmail(new Email("test2@example.com"))) {
            memberRepository.save(new Member("test2@example.com", "5678"));
        }

        Exercise exercise = new Exercise("헬스",
            memberRepository.findByEmail(new Email("test@example.com")).orElseThrow());
        exerciseRepository.save(exercise);
        Exercise exercise2 = new Exercise("요가",
            memberRepository.findByEmail(new Email("test2@example.com")).orElseThrow());
        exerciseRepository.save(exercise2);

        ExerciseTime exerciseTime = new ExerciseTime(exercise);
        ExerciseTime exerciseTime2 = new ExerciseTime(exercise2);
        exerciseTimeRepository.save(exerciseTime2);
        exerciseTime.startExercise();
        exerciseTimeRepository.save(exerciseTime);
    }

    @Test
    @DisplayName("스케줄링 작업 - 새벽 3시에 운동 기록 저장 및 시간 초기화")
    void testSaveAllExerciseHistoryAt3AM() {
        exerciseSchedulerService.saveAllExerciseHistoryAt3AM();

        List<ExerciseTime> exerciseTimes = exerciseTimeRepository.findAll();
        Member member = memberRepository.findByEmail(new Email("test@example.com"))
            .orElseThrow();
        List<ExerciseHistory> exerciseHistories = exerciseHistoryRepository.findAll();

        // 3시 이후 운동이 강제 종료 되는가
        assertFalse(exerciseTimes.get(0).isActive());

        // exerciseTime이 0으로 초기화 되는가
        assertEquals(Duration.ZERO, exerciseTimes.get(0).getExerciseTime());

        // 운동 기록이 있는 멤버의 출석일이 증가하는가
        assertTrue(member.getExerciseAttendanceDate() > 0);
    }

    @Test
    @DisplayName("스케줄링 작업 - 운동 기록이 없는 멤버는 출석일이 증가하지 않음")
    void testSaveAllExerciseHistoryAt3AM_WithNoExerciseTimeForOneMember() {
        exerciseSchedulerService.saveAllExerciseHistoryAt3AM();

        List<ExerciseTime> exerciseTimes = exerciseTimeRepository.findAll();
        Member member2 = memberRepository.findByEmail(new Email("test2@example.com"))
            .orElseThrow();
        List<ExerciseHistory> exerciseHistories = exerciseHistoryRepository.findAll();

        // 두 번째 멤버의 출석일이 증가하지 않았는지 확인
        assertEquals(0, member2.getExerciseAttendanceDate());
    }

    @Test
    @DisplayName("스케줄링 작업 - 모든 멤버의 운동 기록 저장")
    void testSaveAllExerciseHistoryForAllMembers() {
        exerciseSchedulerService.saveAllExerciseHistoryAt3AM();

        List<ExerciseHistory> exerciseHistories = exerciseHistoryRepository.findAll();

        // 운동 기록이 2개 저장되었는가
        assertEquals(2, exerciseHistories.size());
    }

}