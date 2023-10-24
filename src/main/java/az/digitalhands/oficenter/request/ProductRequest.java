package az.digitalhands.oficenter.request;

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
    private String status;
    private Long categoryId;

}