package az.digitalhands.oficenter.exception.error;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorMessage {

    public static final String USER_NOT_FOUND = "User not found. ";
    public static final String INVALID_DATA = "Invalid data. ";
    public static final String BAD_CREDENTIALS = "Bad credentials. ";
    public static final String INCORRECT_PASSWORD = "The password you entered is incorrect: ";
    public static final String NOT_MATCHES = "The passwords you entered do not match: ";
    public static final String COLLECTION_NOT_FOUND = "Collection not found : ";
    public static final String CATEGORY_NOT_FOUND = "Category not found : ";
    public static final String PRODUCT_NOT_FOUND = "Product not found : ";
    public static final String SHOP_NOT_FOUND = "Shop not found : ";
    public static final String ERROR_IMAGE_DOWNLOAD = "Error image download : ";
    public static final String BLOG_POST_NOT_FOUND = "BlogPost not found: ";

}