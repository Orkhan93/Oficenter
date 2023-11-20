package az.digitalhands.oficenter.mappers;

import az.digitalhands.oficenter.domain.About;
import az.digitalhands.oficenter.request.AboutRequest;
import az.digitalhands.oficenter.response.AboutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AboutMapper {

    About fromRequestToModel(AboutRequest aboutRequest);

    AboutResponse fromModelToResponse(About about);

}