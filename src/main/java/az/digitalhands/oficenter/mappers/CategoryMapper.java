package az.digitalhands.oficenter.mappers;

import az.digitalhands.oficenter.domain.Category;
import az.digitalhands.oficenter.request.CategoryRequest;
import az.digitalhands.oficenter.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryResponse fromRequestToResponse(CategoryRequest categoryRequest);

    Category fromRequestToModel(CategoryRequest categoryRequest);

    CategoryResponse fromModelToResponse(Category category);

}