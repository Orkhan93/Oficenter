package az.digitalhands.oficenter.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerRequest {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;

}