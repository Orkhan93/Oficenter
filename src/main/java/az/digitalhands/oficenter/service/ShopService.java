package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.Shop;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.ShopNotFoundException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.ShopMapper;
import az.digitalhands.oficenter.repository.ShopRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.ShopRequest;
import az.digitalhands.oficenter.response.ShopResponse;
import az.digitalhands.oficenter.wrapper.ShopWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopMapper shopMapper;

    public ResponseEntity<ShopResponse> createShop(ShopRequest shopRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Shop shop = shopMapper.fromRequestToModel(shopRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(shopMapper.fromModelToResponse(shopRepository.save(shop)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<ShopResponse> updateShop(ShopRequest shopRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Shop findShop = shopRepository.findById(shopRequest.getId()).orElseThrow(
                    () -> new ShopNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.SHOP_NOT_FOUND));
            if (Objects.nonNull(findShop)) {
                return ResponseEntity.status(HttpStatus.OK).body(shopMapper.fromModelToResponse
                        (shopRepository.save(shopMapper.fromRequestToModel(shopRequest))));
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<List<ShopWrapper>> getAllShops() {
        return ResponseEntity.status(HttpStatus.OK).body(shopRepository.getAllShops());
    }

    public ResponseEntity<ShopResponse> getShopById(Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(
                () -> new ShopNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.SHOP_NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).body(shopMapper.fromModelToResponse(shop));
    }

    public void deleteById(Long userId, Long shopId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Shop shop = shopRepository.findById(shopId).orElseThrow(
                    () -> new ShopNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.SHOP_NOT_FOUND));
            shopRepository.deleteById(shopId);
            log.info("deleteShop {}", shop);
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void deleteAllShops(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            shopRepository.deleteAll();
            log.info("deleteAllShops successfully");
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}