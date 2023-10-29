package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.response.CartResponse;
import az.digitalhands.oficenter.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addItemsToCart(@RequestParam(name = "productId") Long productId,
                                                       @RequestParam(name = "quantity", required = false, defaultValue = "1") Integer quantity,
                                                       @RequestParam(name = "email") String email) {
        return cartService.addItemToCart(productId, quantity, email);
    }

    @PostMapping("/update")
    public ResponseEntity<CartResponse> updateCart(@RequestParam(name = "productId") Long productId,
                                                   @RequestParam(name = "quantity", required = false, defaultValue = "1") Integer quantity,
                                                   @RequestParam(name = "email") String email) {
        return cartService.updateCart(productId, quantity, email);
    }

    @GetMapping("/get/{customerEmail}")
    public ResponseEntity<CartResponse> getCartByCustomerEmail(@PathVariable(name = "customerEmail") String customerEmail) {
        return cartService.getCartByCustomerEmail(customerEmail);
    }

}