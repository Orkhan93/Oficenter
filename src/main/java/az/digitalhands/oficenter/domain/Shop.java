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
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "workTime")
    private String workTime;

    @Column(name = "phone")
    private String phone;

    @Override
    public String toString() {
        return "Shop{id=%d, name='%s', address='%s', email='%s', workTime='%s', phone='%s'}"
                .formatted(id, name, address, email, workTime, phone);
    }

}