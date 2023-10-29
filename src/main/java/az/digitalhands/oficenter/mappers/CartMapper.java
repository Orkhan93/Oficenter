package az.digitalhands.oficenter.mappers;

import az.digitalhands.oficenter.domain.Cart;
import az.digitalhands.oficenter.request.CartRequest;
import az.digitalhands.oficenter.response.CartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    Cart fromRequestToModel(CartRequest cartRequest);

    CartResponse fromModelToResponse(Cart cart);

}