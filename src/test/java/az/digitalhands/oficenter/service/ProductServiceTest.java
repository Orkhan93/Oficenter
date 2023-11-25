package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.Product;
import az.digitalhands.oficenter.exception.CategoryNotFoundException;
import az.digitalhands.oficenter.exception.ProductNotFoundException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.mappers.ProductMapper;
import az.digitalhands.oficenter.mappers.ProductMapperImpl;
import az.digitalhands.oficenter.repository.CategoryRepository;
import az.digitalhands.oficenter.repository.ProductRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.response.ProductResponse;
import az.digitalhands.oficenter.util.Util;
import az.digitalhands.oficenter.wrapper.ProductWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ProductMapper productMapper = new ProductMapperImpl();

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        Optional<Product> product = Optional.of(Util.product());
        when(productRepository.findById(1L)).thenReturn(product);
    }

    @Test
    public void testGetProduct_whenGetProductById_shouldReturnProductResponse() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(Util.product()));
        when(productRepository.findById(0L)).thenThrow(ProductNotFoundException.class);
        String name = "product-name";
        ProductResponse product = productService.getProductById(1L);
        assertEquals(name, product.getName());
        assertThat(product).isNotNull();

        verify(productRepository).findById(Util.product().getId());
    }

    @Test
    public void testAddProduct_whenCreateProductCalledWithValidRequest_itShouldReturnProductResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(Util.user()));
        when(userRepository.findById(0L)).thenThrow(UserNotFoundException.class);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(Util.category()));
        when(categoryRepository.findById(0L)).thenThrow(CategoryNotFoundException.class);
        when(productRepository.save(Util.product())).thenReturn(Util.product());

        productService.createProduct(Util.productRequest(), Util.user().getId());

        verify(userRepository).findById(1L);
        verify(categoryRepository).findById(1L);
    }

    @Test
    public void testFindAllProducts_whenGetAllProducts_shouldReturnAllProductWrapper() {
        List<Product> productList = new ArrayList<>();
        productList.add(Util.product());
        productList.add(Util.product2());

        when(productRepository.findAll()).thenReturn(productList);
        assertThat(productList).isNotNull();
        assertTrue(productList.size() > 1);

        List<ProductWrapper> allProducts = productRepository.getAllProducts();
        allProducts.add(Util.productWrapper());
        allProducts.add(Util.productWrapper2());

        when(productRepository.getAllProducts()).thenReturn(allProducts);

        verify(productRepository).getAllProducts();
    }

}