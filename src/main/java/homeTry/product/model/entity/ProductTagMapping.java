package homeTry.product.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductTagMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long productTagId;


    protected ProductTagMapping() {

    }

    public ProductTagMapping(Long productId, Long productTagId) {
        this.productId = productId;
        this.productTagId = productTagId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getProductTagId() {
        return productTagId;
    }

}
