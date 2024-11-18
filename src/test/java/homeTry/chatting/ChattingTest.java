package homeTry.chatting;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import homeTry.chatting.model.entity.Chatting;
import homeTry.chatting.repository.ChattingRepository;
import homeTry.common.auth.jwt.JwtAuth;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.repository.MemberRepository;
import homeTry.team.model.entity.Team;
import homeTry.team.model.entity.TeamMemberMapping;
import homeTry.team.repository.TeamMemberMappingRepository;
import homeTry.team.repository.TeamRepository;
import java.util.ArrayList;
import java.util.List;
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

@SpringBootTest
@AutoConfigureMockMvc
public class ChattingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChattingRepository chattingRepository;

    @Autowired
    private TeamMemberMappingRepository teamMemberMappingRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private JwtAuth jwtAuth;

    private String token;
    private Long testMemberId;
    private TeamMemberMapping testTeamMemberMapping;

    private final List<Long> chattingIdList = new ArrayList<>();



    @BeforeEach
    void beforeEach() throws Exception {
        Member testMember = new Member("test@test.com", "1234");
        testMemberId = memberRepository.save(testMember).getId();

        token = jwtAuth.generateToken(MemberDTO.from(testMember));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/team/join/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

        Team team = teamRepository.findById(1L).get();

        testTeamMemberMapping = teamMemberMappingRepository.findByTeamAndMember(team,
                testMember).get();

        for (int i = 0; i < 30; i++) {
            chattingIdList.add(
                    chattingRepository.save(new Chatting(testTeamMemberMapping, "테스트 메세지" + i))
                            .getId());
        }
    }

    @AfterEach
    void afterEach() throws Exception {
        for (Long id : chattingIdList)
            chattingRepository.deleteById(id);

        teamMemberMappingRepository.delete(testTeamMemberMapping);

        memberRepository.deleteById(testMemberId);
    }

    @Test
    @DisplayName("채팅 내역 가져오기 테스트")
    void restChattingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/team/chatting/1?page=3&size=5")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }


}


