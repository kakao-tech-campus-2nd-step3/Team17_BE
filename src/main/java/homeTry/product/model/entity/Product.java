package homeTry.product.model.entity;

import homeTry.common.entity.SoftDeletableEntity;
import homeTry.product.model.vo.*;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "image_url"))
    private ProductImageUrl imageUrl;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "product_url", columnDefinition = "TEXT"))
    private ProductUrl productUrl;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private ProductName name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private ProductPrice price;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "store_name"))
    private StoreName storeName;

    @Column(nullable = false)
    private Long viewCount = 0L;

    protected Product() {

    }

    public Product(String imageUrl, String productUrl, String name, Long price, String storeName) {
        this.imageUrl = new ProductImageUrl(imageUrl);
        this.productUrl = new ProductUrl(productUrl);
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
        this.storeName = new StoreName(storeName);
        this.viewCount = 0L;
    }

    // 상품 수정
    public void update(String imageUrl, String productUrl, String name, Long price, String storeName) {
        this.imageUrl = new ProductImageUrl(imageUrl);
        this.productUrl = new ProductUrl(productUrl);
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
        this.storeName = new StoreName(storeName);
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl.value();
    }

    public String getProductUrl() {
        return productUrl.value();
    }

    public String getName() {
        return name.value();
    }

    public Long getPrice() {
        return price.value();
    }

    public String getStoreName() {
        return storeName.value();
    }

    public Long getViewCount() {
        return viewCount;
    }

}
