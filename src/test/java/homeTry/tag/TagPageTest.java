package homeTry.tag;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import homeTry.common.auth.jwt.JwtAuth;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.repository.MemberRepository;
import homeTry.tag.productTag.dto.request.ProductTagRequest;
import homeTry.tag.productTag.model.entity.ProductTag;
import homeTry.tag.productTag.repository.ProductTagRepository;
import homeTry.tag.teamTag.dto.request.TeamTagRequest;
import homeTry.tag.teamTag.model.entity.TeamTag;
import homeTry.tag.teamTag.repository.TeamTagRepository;
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
public class TagPageTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamTagRepository teamTagRepository;

    @Autowired
    private ProductTagRepository productTagRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtAuth jwtAuth;

    private String token;
    private Member member;
    private Member savedMember;

    private ProductTag savedProductTag;
    private TeamTag savedTeamTag;

    @BeforeEach
    void beforeEach() {

        member = new Member("test@test.com", "1234");
        member.promoteToAdmin();
        savedMember = memberRepository.save(member);
        token = jwtAuth.generateToken(MemberDTO.from(savedMember));

        savedProductTag = productTagRepository.save(new ProductTag("testProductTag"));
        savedTeamTag = teamTagRepository.save(new TeamTag("testTeamTag", "testAttribute"));
    }


    @Test
    @DisplayName("상품 태그 목록 페이지 요청 테스트")
    void showProductTagListTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/page/tag/product")
                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(view().name("tag/productTags"))
                        .andExpect(model().attributeExists("productTags"));
    }

    @Test
    @DisplayName("팀 태그 목록 페이지 요청 테스트")
    void showTeamTagListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/page/tag/team")
                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(view().name("tag/teamTags"))
                        .andExpect(model().attributeExists("teamTags"));
    }

    @Test
    @DisplayName("상품 태그 추가 폼 페이지 요청 테스트")
    void showProductTagFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/page/tag/product/add")
                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(view().name("tag/addProductTag"));
    }

    @Test
    @DisplayName("상품 태그 저장 테스트")
    void saveProductTagTest() throws Exception {

        String productTagRequestJson = """
            {
                "productTagName": "testTag"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/page/tag/product")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productTagRequestJson))
                        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("팀 태그 추가 폼 페이지 요청 테스트")
    void showTeamTagFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/page/tag/team/add")
                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(view().name("tag/addTeamTag"));
    }

    @Test
    @DisplayName("팀 태그 저장 테스트")
    void saveTeamTagTest() throws Exception {

        String teamTagRequestJson = """
            {
                "teamTagName": "testTag",
                "teamTagAttribute": "testAttribute"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/page/tag/team")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamTagRequestJson))
                        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("상품 태그 삭제 테스트")
    void deleteProductTagTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/page/tag/product/" + savedProductTag.getId())
                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("팀 태그 삭제 테스트")
    void deleteTeamTagTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/page/tag/team/" + savedTeamTag.getId())
                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isNoContent());
    }
}
