package az.digitalhands.oficenter.mappers;

import az.digitalhands.oficenter.domain.Shop;
import az.digitalhands.oficenter.request.ShopRequest;
import az.digitalhands.oficenter.response.ShopResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopMapper {

    ShopResponse fromRequestToResponse(ShopRequest shopRequest);

    Shop fromRequestToModel(ShopRequest shopRequest);

    ShopResponse fromModelToResponse(Shop shop);

    List<ShopResponse> fromModelListToResponseList(List<Shop> shops);

}
