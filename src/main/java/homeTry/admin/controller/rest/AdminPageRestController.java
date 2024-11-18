package homeTry.admin.controller.rest;

import homeTry.admin.dto.request.AdminCodeRequest;
import homeTry.admin.service.AdminPageService;
import homeTry.common.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Admin Page Rest", description = "관리자 페이지 Rest API")
@RequestMapping("/admin")
public class AdminPageRestController {

    private final AdminPageService adminPageService;

    public AdminPageRestController(AdminPageService adminPageService) {
        this.adminPageService = adminPageService;
    }

    @PutMapping("/promote")
    @Operation(summary = "관리자 신청", description = "클라이언트가 '주장'하는 Admin 코드와 서버와 대조하여 관리자로 업그레이드 시킨다.")
    @ApiResponse(responseCode = "200", description = "관리자로 성공적으로 업그레이드 함")
    public ResponseEntity<Void> promoteToAdmin(
            @RequestBody AdminCodeRequest adminCodeRequest, @LoginMember MemberDTO memberDTO) {
        adminPageService.promoteAdmin(adminCodeRequest, memberDTO.id());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
