package homeTry.tag.controller.view;

import homeTry.tag.productTag.dto.ProductTagDto;
import homeTry.tag.productTag.service.ProductTagService;
import homeTry.tag.teamTag.dto.TeamTagDTO;
import homeTry.tag.teamTag.service.TeamTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/page/tag")
@Tag(name = "Admin Tag View Controller", description = "태그 관리 SSR view 엔드포인트")
public class AdminTagViewController {

    private final TeamTagService teamTagService;
    private final ProductTagService productTagService;

    public AdminTagViewController(TeamTagService teamTagService, ProductTagService productTagService) {
        this.teamTagService = teamTagService;
        this.productTagService = productTagService;
    }

    @GetMapping("/product")
    @Operation(summary = "상품 태그 리스트 페이지 불러오기", description = "상품 태그 리스트 페이지를 응답한다.")
    @ApiResponse(responseCode = "200", description = "상품 태그 리스트 페이지를 성공적으로 응답함", content = @Content(mediaType = "text/html"))
    public String showProductTagList(Model model) {
        List<ProductTagDto> productTags = productTagService.getProductTagResponse().productTags();
        model.addAttribute("productTags", productTags);
        return "tag/productTags";
    }

    @GetMapping("/team")
    @Operation(summary = "팀 태그 리스트 페이지 불러오기", description = "팀 태그 리스트 페이지를 응답한다.")
    @ApiResponse(responseCode = "200", description = "팀 태그 리스트 페이지를 성공적으로 응답함", content = @Content(mediaType = "text/html"))
    public String showTeamTagList(Model model) {
        List<TeamTagDTO> teamTags = teamTagService.getTeamTagResponse().teamTags();
        model.addAttribute("teamTags", teamTags);
        return "tag/teamTags";
    }

    @GetMapping("/product/add")
    @Operation(summary = "상품 태그 추가 페이지 불러오기", description = "상품 태그 추가 페이지를 응답한다.")
    @ApiResponse(responseCode = "200", description = "상품 태그 추가 페이지를 성공적으로 응답함", content = @Content(mediaType = "text/html"))
    public String showProductTagForm() {
        return "tag/addProductTag";
    }

    @GetMapping("/team/add")
    @Operation(summary = "팀 태그 추가 페이지 불러오기", description = "팀 태그 추가 페이지를 응답한다.")
    @ApiResponse(responseCode = "200", description = "팀 태그 추가 페이지를 성공적으로 응답함", content = @Content(mediaType = "text/html"))
    public String showTeamTagFrom() {
        return "tag/addTeamTag";
    }

}