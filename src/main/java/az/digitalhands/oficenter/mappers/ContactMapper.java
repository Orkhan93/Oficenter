package az.digitalhands.oficenter.mappers;

import az.digitalhands.oficenter.domain.Contact;
import az.digitalhands.oficenter.request.ContactRequest;
import az.digitalhands.oficenter.response.ContactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ContactMapper {

    Contact fromRequestToModel(ContactRequest contactRequest);

    ContactResponse fromModelToResponse(Contact contact);

    ContactResponse fromRequestToResponse(ContactRequest contactRequest);

}