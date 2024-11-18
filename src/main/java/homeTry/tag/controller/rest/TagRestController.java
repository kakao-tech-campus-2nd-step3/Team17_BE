package homeTry.tag.controller.rest;

import homeTry.tag.productTag.dto.request.ProductTagRequest;
import homeTry.tag.productTag.service.ProductTagService;
import homeTry.tag.teamTag.dto.request.TeamTagRequest;
import homeTry.tag.teamTag.service.TeamTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/page/tag")
@Tag(name = "Admin Tag", description = "태그 관리자 API")
public class TagRestController {

    private final ProductTagService productTagService;
    private final TeamTagService teamTagService;

    public TagRestController(ProductTagService productTagService, TeamTagService teamTagService) {
        this.productTagService = productTagService;
        this.teamTagService = teamTagService;
    }

    @PostMapping("/product")
    @Operation(summary = "상품 태그 생성", description = "ProductTagRequest를 받아 상품 태그 생성")
    @ApiResponse(responseCode = "201", description = "상품 태그 생성 성공")
    public ResponseEntity<Void> addProductTag(@RequestBody @Valid ProductTagRequest productTagRequest) {
        productTagService.addProductTag(productTagRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{productTagId}")
    @Operation(summary = "상품 태그 삭제", description = "productTagId로 상품 태그 삭제")
    @ApiResponse(responseCode = "204", description = "상품 태그 삭제 성공")
    public ResponseEntity<Void> deleteProductTag(@PathVariable("productTagId") Long productTagId) {
        productTagService.deleteProductTag(productTagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/team")
    @Operation(summary = "팀 태그 생성", description = "teamTagRequest를 받아 상품 태그 생성")
    @ApiResponse(responseCode = "201", description = "팀 태그 생성 성공")
    public ResponseEntity<Void> addTeamTag(@RequestBody @Valid TeamTagRequest teamTagRequest) {
        teamTagService.addTeamTag(teamTagRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/team/{teamTagId}")
    @Operation(summary = "팀 태그 삭제", description = "teamTagId로 상품 태그 삭제")
    @ApiResponse(responseCode = "204", description = "팀 태그 삭제 성공")
    public ResponseEntity<Void> deleteTeamTag(@PathVariable("teamTagId") Long teamTagId) {
        teamTagService.deleteTeamTag(teamTagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
