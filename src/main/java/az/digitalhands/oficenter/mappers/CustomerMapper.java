package az.digitalhands.oficenter.mappers;

import az.digitalhands.oficenter.domain.Customer;
import az.digitalhands.oficenter.request.CustomerRequest;
import az.digitalhands.oficenter.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    Customer fromRequestToModel(CustomerRequest customerRequest);

    CustomerResponse fromModelToResponse(Customer customer);

    CustomerResponse fromRequestToResponse(CustomerRequest customerRequest);

}