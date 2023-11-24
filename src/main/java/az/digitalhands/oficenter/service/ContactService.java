package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.Contact;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.exception.ContactNotFoundException;
import az.digitalhands.oficenter.exception.UnauthorizedException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.ContactMapper;
import az.digitalhands.oficenter.repository.ContactRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.ContactRequest;
import az.digitalhands.oficenter.response.ContactResponse;
import az.digitalhands.oficenter.response.ContactResponseList;
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

    public ContactResponse addContact(ContactRequest contactRequest) {
        log.info("Inside addContact {}", contactRequest);
        return contactMapper.fromModelToResponse
                (contactRepository.save(contactMapper.fromRequestToModel(contactRequest)));
    }

    public ContactResponseList getAllContacts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user)) {
            log.info("Inside getAllContacts {}", contactRepository.getAllContacts());
            List<Contact> contacts = contactRepository.findAll();
            ContactResponseList list = new ContactResponseList();
            List<ContactResponse> contactResponses = contactMapper.fromModelListToResponseList(contacts);
            list.setContactResponses(contactResponses);
            return list;
        }
        throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name(), ErrorMessage.UNAUTHORIZED);
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