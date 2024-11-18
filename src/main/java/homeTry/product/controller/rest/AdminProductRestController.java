package homeTry.product.controller.rest;

import homeTry.product.dto.request.ProductRequest;
import homeTry.product.service.AdminProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin Product", description = "상품 관리자 API")
@RestController
@RequestMapping("/admin/page/product")
public class AdminProductRestController {

    private final AdminProductService adminProductService;

    public AdminProductRestController(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    // 상품 추가
    @PostMapping
    @Operation(summary = "상품 추가", description = "새로운 상품 추가")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품이 성공적으로 추가됨")
    })
    public ResponseEntity<Void> addProduct(@RequestBody @Valid ProductRequest productRequest) {

        adminProductService.addProduct(productRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 상품 수정
    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정", description = "기존 상품 정보 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품이 성공적으로 수정됨")
    })
    public ResponseEntity<Void> editProduct(@PathVariable("productId") Long productId,
            @RequestBody @Valid ProductRequest productRequest) {

        adminProductService.updateProduct(productId, productRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "특정 상품 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "상품이 성공적으로 샥제됨")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {

        adminProductService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
