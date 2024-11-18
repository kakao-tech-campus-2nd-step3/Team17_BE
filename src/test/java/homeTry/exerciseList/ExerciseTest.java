package homeTry.exerciseList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import homeTry.common.auth.jwt.JwtAuth;
import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseTime;
import homeTry.exerciseList.repository.ExerciseHistoryRepository;
import homeTry.exerciseList.repository.ExerciseRepository;
import homeTry.exerciseList.repository.ExerciseTimeRepository;
import homeTry.exerciseList.service.ExerciseTimeService;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
import homeTry.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public class ExerciseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseTimeRepository exerciseTimeRepository;

    @Autowired
    private ExerciseHistoryRepository exerciseHistoryRepository;

    @Autowired
    private ExerciseTimeService exerciseTimeService;

    @Autowired
    private JwtAuth jwtAuth;

    private String token;

    @BeforeEach
    void beforeEach() {
        exerciseHistoryRepository.deleteAll();
        exerciseTimeRepository.deleteAll();
        exerciseRepository.deleteAll();

        Member testMember = memberRepository.findByEmail(new Email("test@example.com"))
            .orElseGet(() -> memberRepository.save(new Member("test@example.com", "1234")));
        token = jwtAuth.generateToken(MemberDTO.from(testMember));
    }

    @Test
    @DisplayName("운동 생성")
    void createExerciseTest() throws Exception {
        String exerciseRequestJson = """
            {
                "exerciseName": "헬스"
            }
            """;

        mockMvc.perform(post("/api/exercise")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(exerciseRequestJson))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("운동 삭제")
    void deleteExerciseTest() throws Exception {
        Member testMember = memberRepository.findByEmail(new Email("test@example.com"))
            .orElseThrow(() -> new IllegalStateException("해당 멤버를 찾을 수 없음"));

        Exercise exercise = new Exercise("헬스", testMember);
        exerciseRepository.save(exercise);

        mockMvc.perform(delete("/api/exercise/" + exercise.getExerciseId())
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("운동 시작")
    void startExerciseTest() throws Exception {
        Member testMember = memberRepository.findByEmail(new Email("test@example.com"))
            .orElseThrow(() -> new IllegalStateException("해당 멤버를 찾을 수 없음"));

        Exercise exercise = new Exercise("헬스", testMember);
        exerciseRepository.save(exercise);

        ExerciseTime exerciseTime = new ExerciseTime(exercise);
        exerciseTimeRepository.save(exerciseTime);

        mockMvc.perform(post("/api/exercise/" + exercise.getExerciseId())
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());

    }

    @Test
    @DisplayName("운동 종료")
    void stopExerciseTest() throws Exception {
        Member testMember = memberRepository.findByEmail(new Email("test@example.com"))
            .orElseThrow(() -> new IllegalStateException("해당 멤버를 찾을 수 없음"));

        Exercise exercise = new Exercise("헬스", testMember);
        exerciseRepository.save(exercise);

        ExerciseTime exerciseTime = new ExerciseTime(exercise);
        exerciseTimeRepository.save(exerciseTime);

        // 운동 시작
        exerciseTimeService.startExerciseTime(exercise);

        // 운동 종료
        mockMvc.perform(put("/api/exercise/" + exercise.getExerciseId())
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
    }


}
