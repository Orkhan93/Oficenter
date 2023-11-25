package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.request.ShopRequest;
import az.digitalhands.oficenter.response.ShopResponse;
import az.digitalhands.oficenter.response.ShopResponseList;
import az.digitalhands.oficenter.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<ShopResponse> createShop(@RequestBody ShopRequest shopRequest,
                                                   @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(shopService.createShop(shopRequest, userId));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ShopResponse> updateShop(@RequestBody ShopRequest shopRequest,
                                                   @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(shopService.updateShop(shopRequest, userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ShopResponseList> getAllShops() {
        return ResponseEntity.status(HttpStatus.OK).body(shopService.getAllShops());
    }

    @GetMapping("/get/{shopId}")
    public ResponseEntity<ShopResponse> getShopById(@PathVariable(name = "shopId") Long shopId) {
        return ResponseEntity.status(HttpStatus.OK).body(shopService.getShopById(shopId));
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