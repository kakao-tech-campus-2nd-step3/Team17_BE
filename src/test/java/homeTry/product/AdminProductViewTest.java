package homeTry.product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
class AdminProductViewTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtAuth jwtAuth;

    private String adminToken;

    @BeforeEach
    void setUp() {
        Member adminMember = memberRepository.save(new Member("admin@example.com", "admin"));
        adminMember.promoteToAdmin();
        memberRepository.save(adminMember);
        adminToken = jwtAuth.generateToken(MemberDTO.from(adminMember));
    }

    @Test
    @DisplayName("관리자 - 상품 목록 페이지")
    void testGetProducts() throws Exception {
        mockMvc.perform(get("/admin/page/product")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk())
            .andExpect(view().name("product/productList"))
            .andExpect(model().attributeExists("products"));
    }

    @Test
    @DisplayName("관리자 - 상품 추가 폼 페이지")
    void testShowAddProductForm() throws Exception {
        mockMvc.perform(get("/admin/page/product/add")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk())
            .andExpect(view().name("product/productAdd"))
            .andExpect(model().attributeExists("productRequest"))
            .andExpect(model().attributeExists("tags"));
    }

    @Test
    @DisplayName("관리자 - 상품 추가 요청 (REST API)")
    void testAddProduct() throws Exception {
        String productRequestJson = """
        {
            "imageUrl": "https://example.com/image.jpg",
            "productUrl": "https://example.com/product",
            "name": "상품명",
            "price": 10000,
            "storeName": "스토어명",
            "tagId": 1
        }
        """;

        mockMvc.perform(post("/admin/page/product")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestJson))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("관리자 - 상품 수정 요청 (REST API)")
    void testEditProductWithoutObjectMapper() throws Exception {
        Long productId = 1L;
        String productRequestJson = """
            {
                "imageUrl": "https://example.com/image_updated.jpg",
                "productUrl": "https://example.com/product_updated",
                "name": "수정된 상품명",
                "price": 15000,
                "storeName": "수정된 스토어명",
                "tagId": 1
            }
            """;

        mockMvc.perform(put("/admin/page/product/{productId}", productId)
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestJson))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("관리자 - 상품 삭제 요청 (REST API)")
    void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/admin/page/product/{productId}", productId)
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isNoContent());
    }

}