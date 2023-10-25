package az.digitalhands.oficenter.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerResponse {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;

}