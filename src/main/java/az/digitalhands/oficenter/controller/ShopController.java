package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.request.ShopRequest;
import az.digitalhands.oficenter.response.ShopResponse;
import az.digitalhands.oficenter.service.ShopService;
import az.digitalhands.oficenter.wrapper.ShopWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<ShopResponse> createShop(@RequestBody ShopRequest shopRequest,
                                                   @PathVariable(name = "userId") Long userId) {
        return shopService.createShop(shopRequest, userId);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ShopResponse> updateShop(@RequestBody ShopRequest shopRequest,
                                                   @PathVariable(name = "userId") Long userId) {
        return shopService.updateShop(shopRequest, userId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ShopWrapper>> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/get/{shopId}")
    public ResponseEntity<ShopResponse> getShopById(@PathVariable(name = "shopId") Long shopId) {
        return shopService.getShopById(shopId);
    }

    @DeleteMapping("/{userId}/delete/{shopId}")
    public void deleteShop(@PathVariable(name = "userId") Long userId,
                           @PathVariable(name = "shopId") Long shopId) {
        shopService.deleteById(userId, shopId);
    }

    @DeleteMapping("/{userId}/deleteAll")
    public void deleteAllShops(@PathVariable(name = "userId") Long userId) {
        shopService.deleteAllShops(userId);
    }

}
