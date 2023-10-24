package az.digitalhands.oficenter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@NamedQuery(name ="Shop.getAllShops",
        query = "select new az.digitalhands.oficenter.wrapper.ShopWrapper(s.id,s.name,s.address,s.email,s.workTime,s.phone)  from Shop s")
@Getter
@Setter
@Table(name = "shop")
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String email;
    private String workTime;
    private String phone;

    @Override
    public String toString() {
        return "Shop{id=%d, name='%s', address='%s', email='%s', workTime='%s', phone='%s'}"
                .formatted(id, name, address, email, workTime, phone);
    }
}
