package az.digitalhands.oficenter.domain;

import az.digitalhands.oficenter.enums.StatusRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@NamedQuery(name = "Product.getAllProducts",
        query = "select new az.digitalhands.oficenter.wrapper.ProductWrapper" +
                "(p.id,p.name,p.description,p.quantity,p.price,p.status,p.imageOfProduct,p.category.id) from Product p")
@NamedQuery(name = "Product.getAllProductsStatusTrue", query = "select new az.digitalhands.oficenter.wrapper.ProductWrapper" +
        "(p.id,p.name,p.description,p.quantity,p.price,p.status,p.imageOfProduct,p.category.id)from Product p where p.status='TRUE'")

@Entity
@Setter
@Getter
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusRole status;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "image")
    private String imageOfProduct;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Override
    public String toString() {
        return "Product{id=%d, name='%s', description='%s', status=%s, price=%s, quantity=%d, imageOfProduct='%s'}"
                .formatted(id, name, description, status, price, quantity, imageOfProduct);
    }
}