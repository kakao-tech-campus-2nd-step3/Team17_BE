package homeTry.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import homeTry.chatting.model.entity.Chatting;
import homeTry.chatting.repository.ChattingRepository;
import homeTry.common.auth.jwt.JwtAuth;
import homeTry.exerciseList.repository.ExerciseRepository;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Nickname;
import homeTry.member.repository.MemberRepository;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamMemberMapping;
import homeTry.team.repository.TeamMemberMappingRepository;
import homeTry.team.repository.TeamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
class MemberTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ChattingRepository chattingRepository;

    @Autowired
    private TeamMemberMappingRepository teamMemberMappingRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private JwtAuth jwtAuth;

    private String token;
    private Member testMember;

    @BeforeEach
    void beforeEach() {
        testMember = new Member("test@test.com", "1234");
        memberRepository.save(testMember);

        token = jwtAuth.generateToken(MemberDTO.from(testMember));
    }

    @AfterEach
    void afterEach() {
        memberRepository.delete(testMember);
    }


    @Test
    @DisplayName("회원 프로필 테스트")
    void memberProfileTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/profile")
                        .header("Authorization", "Bearer " + token).contentType(
                                MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("닉네임 변경 테스트")
    void memberNicknameChangeTest() throws Exception {

        String requestJson = """
                {"name" : "4321"}
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/member/profile")
                .header("Authorization", "Bearer " + token).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

        assertThat(memberRepository.findById(testMember.getId()).get()
                .getNickname()).isEqualTo(new Nickname("4321").value());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    @Transactional //원칙적으론 쓰면 안되지만 테스트 끝나고 다 delete 하기엔...
    void memberWithdrawTest() throws Exception {

        String postDiaryRequestBody = """
                {"memo" : "1234"}
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/diary")
                        .header("Authorization", "Bearer " + token).content(postDiaryRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String postTeamRequestBody = """
                {}
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/team/join/1")
                        .header("Authorization", "Bearer " + token).content(postTeamRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String postExerciseRequestBody = """
                {"exerciseName" : "12314123"}
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/exercise")
                        .header("Authorization", "Bearer " + token).content(postExerciseRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Long exerciseId = exerciseRepository.findByMemberId(testMember.getId()).stream()
                .filter(exercise -> exercise.getExerciseName().equals("12314123")).findFirst().get()
                .getExerciseId();

        String postExerciseStartRequestBody = """
                {}
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/exercise/" + exerciseId)
                .header("Authorization", "Bearer " + token).content(postExerciseStartRequestBody)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

        Team team = teamRepository.findById(1L).get();

        TeamMemberMapping testTeamMemberMapping = teamMemberMappingRepository.findByTeamAndMember(
                team,
                testMember).get();

        for (int i = 0; i < 30; i++)
            chattingRepository.save(new Chatting(testTeamMemberMapping, "테스트 메세지" + i));

        //회원 탈퇴하기
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/member/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

    }


}