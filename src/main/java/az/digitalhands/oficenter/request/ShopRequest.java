package az.digitalhands.oficenter.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class ShopRequest {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String workTime;
    private String phone;
}
