package az.digitalhands.oficenter.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private String status;
    @JsonIgnore
    private Long categoryId;

}