package az.digitalhands.oficenter.request;


import az.digitalhands.oficenter.domain.CartItem;
import az.digitalhands.oficenter.domain.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class CartRequest {

    private Long id;
    private Double totalPrice;
    private Integer totalItems;
    private Customer customer;
    private Long productId;
    private Set<CartItem> cartItems;

}