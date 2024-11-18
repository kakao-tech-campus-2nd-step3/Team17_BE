package homeTry.admin.controller.view;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Tag(name = "Admin Page View", description = "관리자 페이지 SSR View 엔트포인트")
@RequestMapping("/admin")
public class AdminPageViewController {

    @GetMapping("/page")
    @Operation(summary = "관리자 페이지 불러오기", description = "관리자 페이지를 불러온다.")
    @ApiResponse(responseCode = "200", description = "관리자 페이지를 성공적으로 불러옴", content = @Content(mediaType = "text/html"))
    public String getAdminPage() {
        return "admin/adminPage";
    }

    @GetMapping("/promote")
    @Operation(summary = "관리자 신청 페이지 불러오기", description = "관리자 신청 페이지를 불러온다.")
    @ApiResponse(responseCode = "200", description = "관리자 신청 페이지를 성공적으로 불러옴", content = @Content(mediaType = "text/html"))
    public String getAdminPromotePage() {
        return "admin/adminPromote";
    }

}