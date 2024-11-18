package homeTry.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import homeTry.common.auth.jwt.JwtAuth;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.repository.MemberRepository;
import homeTry.product.exception.badRequestException.ProductNotFoundException;
import homeTry.product.model.entity.Product;
import homeTry.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
class MarketTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtAuth jwtAuth;

    @Autowired
    private ProductRepository productRepository;

    private String token;

    @BeforeEach
    void beforeEach() {
        Member testMember = memberRepository.save(new Member("test@example.com", "1234"));
        token = jwtAuth.generateToken(MemberDTO.from(testMember));
    }

    @Test
    @DisplayName("상품 조회")
    void testGetProducts() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/market")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content.length()").value(5))
            .andExpect(jsonPath("$.content[0].productId").exists());
    }

    @Test
    @DisplayName("특정 상품 선택")
    void testRedirectProduct() throws Exception {
        Long productId = 1L;

        ResultActions result = mockMvc.perform(get("/api/market/" + productId)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 상품 선택 시 조회수 증가")
    void testIncrementViewCount() throws Exception {
        Long productId = 1L;
        Product productBefore = productRepository.findById(productId).orElseThrow(
            ProductNotFoundException::new);
        Long initialViewCount = productBefore.getViewCount();

        // 특정 상품 선택
        mockMvc.perform(get("/api/market/" + productId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        Product productAfter = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);
        assertEquals(initialViewCount + 1, productAfter.getViewCount());
    }

}