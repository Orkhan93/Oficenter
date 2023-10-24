package az.digitalhands.oficenter.mappers;

import az.digitalhands.oficenter.domain.BlogPost;
import az.digitalhands.oficenter.request.BlogPostRequest;
import az.digitalhands.oficenter.response.BlogPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BlogPostMapper {

    BlogPost fromRequestToModel(BlogPostRequest blogPostRequest);

    BlogPostResponse fromModelToResponse(BlogPost blogPost);


}