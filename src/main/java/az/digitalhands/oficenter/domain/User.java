package az.digitalhands.oficenter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import az.digitalhands.oficenter.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Override
    public String toString() {
        return "User{id=%d, name='%s', username='%s', email='%s', password='%s', userRole=%s}"
                .formatted(id, name, username, email, password, userRole);
    }

}