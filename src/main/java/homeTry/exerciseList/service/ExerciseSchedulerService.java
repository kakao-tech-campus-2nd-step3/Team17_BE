package homeTry.exerciseList.service;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.member.dto.MemberDTO;
import homeTry.member.service.MemberService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExerciseSchedulerService {

    private final ExerciseService exerciseService;
    private final ExerciseTimeService exerciseTimeService;
    private final ExerciseHistoryService exerciseHistoryService;
    private final MemberService memberService;

    public ExerciseSchedulerService(ExerciseService exerciseService,
                                    ExerciseTimeService exerciseTimeService,
                                    ExerciseHistoryService exerciseHistoryService,
                                    MemberService memberService) {
        this.exerciseService = exerciseService;
        this.exerciseTimeService = exerciseTimeService;
        this.exerciseHistoryService = exerciseHistoryService;
        this.memberService = memberService;
    }

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void saveAllExerciseHistoryAt3AM() {
        List<Exercise> allExercises = exerciseService.findAllExercises();
        Set<Long> membersWithExerciseTime = new HashSet<>();

        // 모든 운동 기록을 히스토리에 저장하고 운동 시간을 초기화
        allExercises.forEach(exercise -> {
            ExerciseTime exerciseTime = exerciseTimeService.getExerciseTime(
                    exercise.getExerciseId());

            // exerciseTime 값이 null 이면 넘어감
            if (exerciseTime == null) {
                return;
            }

            // 3시에도 운동이 실행 중이면 강제로 멈추고 저장
            if (exerciseTime.isActive()) {
                exerciseService.stopExercise(exercise.getExerciseId(),
                        MemberDTO.from(exercise.getMember()));
                exerciseTimeService.saveExerciseTime(exerciseTime);
            }

            exerciseHistoryService.saveExerciseHistory(exerciseTime.getExercise(), exerciseTime);
            exerciseTimeService.resetExerciseTime(exerciseTime);

            // 운동 시간이 있는 멤버 ID 저장
            membersWithExerciseTime.add(exercise.getMember().getId());
        });
        
        // 운동 시간이 있는 멤버들의 출석일 증가
        membersWithExerciseTime.forEach(memberService::incrementAttendanceDate);
    }

}
