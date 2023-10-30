package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.Contact;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.exception.ContactNotFoundException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.ContactMapper;
import az.digitalhands.oficenter.repository.ContactRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.ContactRequest;
import az.digitalhands.oficenter.response.ContactResponse;
import az.digitalhands.oficenter.wrapper.ContactWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactMapper contactMapper;

    public ResponseEntity<ContactResponse> addContact(ContactRequest contactRequest) {
        log.info("Inside addContact {}", contactRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactMapper.fromModelToResponse
                        (contactRepository.save(contactMapper.fromRequestToModel(contactRequest))));
    }

    public ResponseEntity<List<ContactWrapper>> getAllContacts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user)) {
            log.info("Inside getAllContacts {}", contactRepository.getAllContacts());
            return ResponseEntity.status(HttpStatus.OK).body(contactRepository.getAllContacts());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void deleteContactById(Long userId, Long contactId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user)) {
            Contact contact = contactRepository.findById(contactId).orElseThrow(
                    () -> new ContactNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CONTACT_NOT_FOUND));
            contactRepository.deleteById(contact.getId());
        }
    }

}