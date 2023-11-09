package az.digitalhands.oficenter.util;

import az.digitalhands.oficenter.domain.Category;
import az.digitalhands.oficenter.domain.Collection;
import az.digitalhands.oficenter.domain.Product;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.StatusRole;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.request.ProductRequest;
import az.digitalhands.oficenter.response.ProductResponse;
import az.digitalhands.oficenter.wrapper.ProductWrapper;

import java.util.ArrayList;

public class Util {

    private Util() {
    }

    public static User user() {
        User user = new User();
        user.setId(1L);
        user.setName("userName");
        user.setPassword("userPassword");
        user.setUserRole(UserRole.ADMIN);
        user.setEmail("user@mail.com");
        return user;
    }


    public static Category category() {
        Category category = new Category();
        category.setId(1L);
        category.setName("categoryName");
        category.setCollection(null);
        return category;
    }

    public static Category category2() {
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("categoryName2");
        category2.setCollection(null);
        return category2;
    }

    public static Collection collection() {
        Collection collection = new Collection();
        collection.setId(1L);
        collection.setName("collectionName");
        collection.setCategories(new ArrayList<>());
        return collection;
    }

    public static Product product() {
        Product product = new Product();
        product.setId(1L);
        product.setName("product-name");
        product.setStatus(StatusRole.FALSE);
        product.setPrice(52.75);
        product.setQuantity(10);
        product.setDescription("description-product");
        product.setImageData("image-product");
        product.setCategory(Util.category());
        return product;
    }

    public static Product product2() {
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("product-name2");
        product2.setStatus(StatusRole.FALSE);
        product2.setPrice(52.75);
        product2.setQuantity(10);
        product2.setDescription("description-product2");
        product2.setImageData("image-product2");
        product2.setCategory(Util.category());
        return product2;
    }

    public static ProductWrapper productWrapper() {
        ProductWrapper productWrapper = new ProductWrapper();
        productWrapper.setId(1L);
        productWrapper.setName("wrapper-product");
        productWrapper.setDescription("wrapper-description");
        productWrapper.setQuantity(10);
        productWrapper.setStatus(StatusRole.TRUE);
        productWrapper.setPrice(25.00);
        productWrapper.setImageData("image-wrapper");
        productWrapper.setCategoryId(category().getId());
        return productWrapper;
    }

    public static ProductWrapper productWrapper2() {
        ProductWrapper productWrapper = new ProductWrapper();
        productWrapper.setId(2L);
        productWrapper.setName("wrapper-product2");
        productWrapper.setDescription("wrapper-description2");
        productWrapper.setQuantity(10);
        productWrapper.setStatus(StatusRole.TRUE);
        productWrapper.setPrice(25.00);
        productWrapper.setImageData("image-wrapper2");
        productWrapper.setCategoryId(category().getId());
        return productWrapper;
    }

    public static ProductRequest productRequest() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(1L);
        productRequest.setName("product-name");
        productRequest.setPrice(52.75);
        productRequest.setDescription("description-product");
        productRequest.setQuantity(10);
        productRequest.setCategoryId(Util.category().getId());
        productRequest.setStatus(StatusRole.FALSE);
        productRequest.setImageOfProduct("image-product");
        return productRequest;
    }

    public static ProductResponse productResponse() {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(1L);
        productResponse.setName("product-name");
        productResponse.setPrice(52.75);
        productResponse.setImageOfProduct("image-product");
        productResponse.setDescription("description-product");
        productResponse.setQuantity(10);
        productResponse.setCategoryId(Util.category().getId());
        productResponse.setStatus(StatusRole.TRUE);
        return productResponse;
    }

}