package az.digitalhands.oficenter.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopWrapper {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String workTime;
    private String phone;
}
