package az.digitalhands.oficenter.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactRequest {

    private Long id;
    private String email;
    private String content;

}