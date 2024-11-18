package homeTry.product.controller.view;

import homeTry.product.dto.request.ProductRequest;
import homeTry.product.dto.response.ProductAdminResponse;
import homeTry.product.service.AdminProductService;
import homeTry.tag.productTag.dto.ProductTagDto;
import homeTry.tag.productTag.service.ProductTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/page/product")
@Tag(name = "Admin Product View", description = "상품 관리 SSR View 엔드포인트")
public class AdminProductViewController {

    private final AdminProductService adminProductService;
    private final ProductTagService productTagService;

    public AdminProductViewController(AdminProductService adminProductService,
        ProductTagService productTagService) {
        this.adminProductService = adminProductService;
        this.productTagService = productTagService;
    }

    // 상품 리스트
    @GetMapping
    @Operation(summary = "상품 리스트 페이지 불러오기", description = "상품 리스트 페이지를 응답한다.")
    @ApiResponse(responseCode = "200", description = "상품 리스트 페이지를 성공적으로 응답함", content = @Content(mediaType = "text/html"))
    public String getProducts(Model model, Pageable pageable) {
        Page<ProductAdminResponse> products = adminProductService.getProducts(pageable);
        model.addAttribute("products", products);
        return "product/productList";
    }

    // 상품 추가 페이지
    @GetMapping("/add")
    @Operation(summary = "상품 추가 페이지 불러오기", description = "상품 추가 페이지를 응답한다.")
    @ApiResponse(responseCode = "200", description = "상품 추가 페이지를 성공적으로 응답함", content = @Content(mediaType = "text/html"))
    public String showAddProductForm(Model model) {
        model.addAttribute("productRequest", new ProductRequest("", "", "", 0L, "", null));
        List<ProductTagDto> tags = productTagService.getProductTagList();
        model.addAttribute("tags", tags);
        return "product/productAdd";
    }

    // 상품 수정 페이지
    @GetMapping("/edit/{productId}")
    @Operation(summary = "상품 수정 페이지 불러오기", description = "상품 수정 페이지를 응답한다.")
    @ApiResponse(responseCode = "200", description = "상품 수정 페이지를 성공적으로 응답함", content = @Content(mediaType = "text/html"))
    public String showEditProductForm(@PathVariable("productId") Long productId, Model model) {
        ProductAdminResponse productResponse = adminProductService.getProductById(productId);
        model.addAttribute("productRequest", productResponse);

        List<ProductTagDto> tags = productTagService.getProductTagList();
        model.addAttribute("tags", tags);

        return "product/productEdit";
    }

}
