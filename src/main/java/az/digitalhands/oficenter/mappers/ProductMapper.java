package az.digitalhands.oficenter.mappers;

import az.digitalhands.oficenter.domain.Product;
import az.digitalhands.oficenter.request.ProductRequest;
import az.digitalhands.oficenter.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product fromRequestToModel(ProductRequest productRequest);

    ProductResponse fromModelToResponse(Product product);

    List<ProductResponse> fromModelListToResponseList(List<Product> productList);

}