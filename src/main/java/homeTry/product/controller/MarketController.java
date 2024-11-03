package homeTry.product.controller;

import homeTry.common.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import homeTry.product.dto.response.ProductResponse;
import homeTry.product.service.ProductService;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final ProductService productService;

    public MarketController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Slice<ProductResponse>> getProducts(
        @RequestParam(required = false) List<Long> tagIds,
        @LoginMember MemberDTO memberDTO,
        @PageableDefault(size = 5)
        @SortDefaults({@SortDefault(sort = "viewCount", direction = Sort.Direction.DESC),
                       @SortDefault(sort = "price", direction = Sort.Direction.ASC)})
        Pageable pageable) {
        Slice<ProductResponse> products = productService.getProducts(tagIds, memberDTO, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);  // 상태 코드 200
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Void> redirectProduct(@PathVariable Long productId,
                                                @LoginMember MemberDTO memberDTO) {
        String productUrl = productService.incrementViewCountAndGetUrl(productId, memberDTO);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(productUrl)).build();
    }

}
