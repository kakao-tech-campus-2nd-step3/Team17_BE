package homeTry.diary;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import homeTry.common.auth.jwt.JwtAuth;
import homeTry.diary.model.entity.Diary;
import homeTry.diary.repository.DiaryRepository;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.repository.MemberRepository;
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
public class DiaryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private JwtAuth jwtAuth;

    private String token;
    private Member testMember;

    private Diary savedDiary;

    @BeforeEach
    void beforeEach() {
        testMember = memberRepository.save( new Member("test@test.com", "1234"));
        token = jwtAuth.generateToken(MemberDTO.from(testMember));

        savedDiary = diaryRepository.save(new Diary("testMemo", testMember));
    }

    @Test
    @DisplayName("일기 생성 테스트")
    void createDiaryTest() throws  Exception{

        String diaryRequestJson = """
            {
                "memo": "testMemo"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/diary")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(diaryRequestJson))
                        .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("일기 삭제 테스트")
    void deleteDiaryTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/diary/" + savedDiary.getId())
                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isNoContent());
    }
}
