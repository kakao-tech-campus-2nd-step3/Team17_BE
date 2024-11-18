package homeTry.common.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import homeTry.common.auth.jwt.JwtAuth;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CorsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtAuth jwtAuth;


    private final MemberDTO memberDTO = new MemberDTO(1L, "1@1.com", "1", Role.USER);


    @Test
    void cors() throws Exception {
        mockMvc.perform(
                        options("/api/member/profile")
                                .header(HttpHeaders.ORIGIN, "http://localhost:123123123")
                                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
                                .header("Authorization", "Bearer " + jwtAuth.generateToken(memberDTO))
                )
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:123123123"))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE,OPTIONS"))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.LOCATION))
                .andDo(print());
    }

}