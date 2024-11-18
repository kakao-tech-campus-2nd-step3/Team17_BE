package homeTry.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import homeTry.common.auth.jwt.JwtAuth;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.repository.MemberRepository;
import homeTry.team.dto.request.CheckingPasswordRequest;
import homeTry.team.dto.request.TeamCreateRequest;
import homeTry.team.model.entity.Team;
import homeTry.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamTest {
    private static final long TEAM_ID_TO_DELETE = 5;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtAuth jwtAuth;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamRepository teamRepository;

    private String token;

    private Member member;

    @BeforeEach
    void beforeEach() {
        member = memberRepository.save(new Member("test@test.com", "test"));
        token = jwtAuth.generateToken(MemberDTO.from(member));
    }

    @Test
    @DisplayName("팀 생성")
    void addTeamTest() throws Exception {
        TeamCreateRequest teamCreateRequest = new TeamCreateRequest(
                "testTeamName",
                "testTeamDescription",
                10,
                null,
                List.of(5L, 6L));

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/team")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamCreateRequest)));

        resultActions
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("팀 삭제")
    void deleteTeamTest() throws Exception {
        Team team = teamRepository.save(new Team(
                "test6Team",
                "test6TeamDescription",
                member,
                10,
                1,
                null));

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/team/" + TEAM_ID_TO_DELETE) //팀 삭제
                        .header("Authorization", "Bearer " + token));

        resultActions
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("팀 검색")
    void SearchingTeamTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/team")
                        .header("Authorization", "Bearer " + token)
                        .param("tagIdList", "1,4")
                        .param("teamName", "test1 team"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].teamName").value("test1 team"))
                .andExpect(jsonPath("$.content[0].teamDescription").value("test1 team description"))
                .andExpect(jsonPath("$.content[0].tagList[0].tagId").value(1))
                .andExpect(jsonPath("$.content[0].tagList[1].tagId").value(4));
    }

    @Test
    @DisplayName("모든 팀태그 조회")
    void getTeamTagListTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/team" + "/teamTags")
                        .header("Authorization", "Bearer " + token));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genderTagList").hasJsonPath())
                .andExpect(jsonPath("$.ageTagList").hasJsonPath())
                .andExpect(jsonPath("$.exerciseIntensityTagList").hasJsonPath());

    }

    @Test
    @DisplayName("랭킹 조회")
    void getTeamRankingTest() throws Exception {
        mockMvc.perform(//1팀에 가입
                MockMvcRequestBuilders.post("/api/team" + "/join/1")
                        .header("Authorization", "Bearer " + token));

        ResultActions resultActions = mockMvc.perform(//1팀의 랭킹 조회
                MockMvcRequestBuilders.get("/api/team" + "/1/ranking")
                        .header("Authorization", "Bearer " + token)
                        .param("date", "20241008"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.myRanking").hasJsonPath())
                .andExpect(jsonPath("$.myNickname").hasJsonPath())
                .andExpect(jsonPath("$.myExerciseTime").hasJsonPath())
                .andExpect(jsonPath("$.slice.content[0]").hasJsonPath())
                .andExpect(jsonPath("$.slice.content[1]").hasJsonPath());
    }

    @Test
    @DisplayName("팀에 가입")
    void joinTeamTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/team" + "/join/1") //1팀에 가입
                        .header("Authorization", "Bearer " + token));

        resultActions
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("팀에서 탈퇴")
    void withdrawTeamTest() throws Exception {
        mockMvc.perform(//1팀에 가입
                MockMvcRequestBuilders.post("/api/team" + "/join/1")
                        .header("Authorization", "Bearer " + token));

        ResultActions resultActions = mockMvc.perform( //1팀 탈퇴
                MockMvcRequestBuilders.delete("/api/team" + "/withdraw/1")
                        .header("Authorization", "Bearer " + token));

        resultActions
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("비밀번호 일치검사")
    void verifyPasswordTest() throws Exception {
        CheckingPasswordRequest checkingPasswordRequest = new CheckingPasswordRequest("1234");

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/team" + "/checking/2")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(checkingPasswordRequest)));

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("내가 가입한 팀 조회")
    void getJoinedTeamTest() throws Exception {
        mockMvc.perform(//1팀에 가입
                MockMvcRequestBuilders.post("/api/team" + "/join/1")
                        .header("Authorization", "Bearer " + token));

        mockMvc.perform(//2팀에 가입
                MockMvcRequestBuilders.post("/api/team" + "/join/2")
                        .header("Authorization", "Bearer " + token));

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/team" + "/joined")
                        .header("Authorization", "Bearer " + token));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2));
    }
}
