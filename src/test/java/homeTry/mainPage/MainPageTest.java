package homeTry.mainPage;

import homeTry.common.auth.jwt.JwtAuth;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainPageTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtAuth jwtAuth;

    private String token;
    private Member testMember;

    @BeforeEach
    void beforeEach() {
        testMember = memberRepository.save( new Member("test@test.com", "1234"));
        token = jwtAuth.generateToken(MemberDTO.from(testMember));
    }

    @Test
    @DisplayName("메인페이지 조회 테스트")
    void getMainPageTest() throws  Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api?date=20241106")
                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isOk());
    }
}
