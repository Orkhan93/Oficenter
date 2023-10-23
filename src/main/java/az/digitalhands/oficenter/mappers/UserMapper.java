package az.digitalhands.oficenter.mappers;


import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.request.UserRequest;
import az.digitalhands.oficenter.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User fromUserSignUpRequestToModel(UserRequest userRequest);


    UserResponse fromModelToResponse(User user);

}