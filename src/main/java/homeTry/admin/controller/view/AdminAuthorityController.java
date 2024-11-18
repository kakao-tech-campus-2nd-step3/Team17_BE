package homeTry.admin.controller.view;

import homeTry.common.annotation.LoginMember;
import homeTry.common.auth.exception.badRequestException.InvalidTokenException;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.enums.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Admin Authority Page View", description = "관리자 권한 페이지 SSR View 엔트포인트")
@Controller
@RequestMapping("/admin")
public class AdminAuthorityController {

    @GetMapping
    @Operation(summary = "관리자 권한 페이지", description = "관리자 권한 페이지를 html로 응답한다.")
    @ApiResponse(responseCode = "200", description = "관리자 권한 페이지를 성공적으로 응답함", content = @Content(mediaType = "text/html"))
    public String getAdminAuthorityPage(@LoginMember MemberDTO memberDTO, Model model) {
        if(memberDTO == null)
            throw new InvalidTokenException();

        if(memberDTO.role() == Role.ADMIN)
            model.addAttribute("role", "admin");

        if(memberDTO.role() == Role.USER)
            model.addAttribute("role", "user");
        
        return "admin/adminAuthority";
    }
}
