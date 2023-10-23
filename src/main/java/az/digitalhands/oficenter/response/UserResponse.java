package az.digitalhands.oficenter.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {

    private Long id;
    private String name;
    private String username;
    private String email;

}