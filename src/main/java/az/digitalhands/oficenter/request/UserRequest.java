package az.digitalhands.oficenter.request;

import az.digitalhands.oficenter.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private UserRole userRole;

}