package az.digitalhands.oficenter.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopResponse {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String workTime;
    private String phone;
}
