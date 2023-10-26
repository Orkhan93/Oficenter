package az.digitalhands.oficenter.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactResponse {

    private Long id;
    private String email;
    private String content;

}