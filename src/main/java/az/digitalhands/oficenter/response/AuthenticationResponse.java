package az.digitalhands.oficenter.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {

    private Long id;
    private String jwtToken;

}