package az.digitalhands.oficenter.mappers;

import az.digitalhands.oficenter.domain.Collection;
import az.digitalhands.oficenter.request.CollectionRequest;
import az.digitalhands.oficenter.response.CollectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CollectionMapper {

    CollectionResponse fromRequestToResponse(CollectionRequest collectionRequest);

    Collection fromRequestToModel(CollectionRequest collectionRequest);

    CollectionResponse fromModelToResponse(Collection collection);

    List<CollectionResponse> fromModelListToResponseList(List<Collection> collections);

}