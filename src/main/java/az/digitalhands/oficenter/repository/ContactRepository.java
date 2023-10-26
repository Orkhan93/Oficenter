package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.Contact;
import az.digitalhands.oficenter.wrapper.ContactWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<ContactWrapper> getAllContacts();

}