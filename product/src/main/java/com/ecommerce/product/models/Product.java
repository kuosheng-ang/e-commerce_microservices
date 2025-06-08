package com.ecommerce.product.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "product_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "101"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    @NotBlank
    @Size(min = 5, message = "Product name must contain at least 5 characters")
    private String productName;

    @NotBlank
    @Size(min = 5, message = "Brand name must contain at least 5 characters")
    private String brand;

    private String image;

    @NotBlank
    @Size(min = 6, message = "Product description must contain at least 6 characters")
    private String description;

    private Integer stockQuantity;

    private double price;

    private double discount;

    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public Product( String productName, String description, final String brand, double price, double discount, double specialPrice, Category category){

        this.productName = productName;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.specialPrice = specialPrice;
        this.category = category;
    }


    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<CartItem> products = new ArrayList<>();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return (productName.equals(that.productName) && brand.equals(that.brand)
                 || category.equals(that.category)) ? true : false;

    }


}
