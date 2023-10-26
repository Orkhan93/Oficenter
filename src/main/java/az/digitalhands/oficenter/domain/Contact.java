package az.digitalhands.oficenter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@NamedQuery(name = "Contact.getAllContacts",
query = "select new az.digitalhands.oficenter.wrapper.ContactWrapper(c.id,c.email,c.content) from Contact c")

@Entity
@Setter
@Getter
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "content")
    private String content;

}