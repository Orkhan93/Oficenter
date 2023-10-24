package az.digitalhands.oficenter.request;

import az.digitalhands.oficenter.enums.StatusRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequest {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private StatusRole status;
    private String imageOfProduct;
    private Long categoryId;

}