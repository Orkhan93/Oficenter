package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.request.ContactRequest;
import az.digitalhands.oficenter.response.ContactResponse;
import az.digitalhands.oficenter.service.ContactService;
import az.digitalhands.oficenter.wrapper.ContactWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/add")
    public ResponseEntity<ContactResponse> addContact(@RequestBody ContactRequest contactRequest) {
        return contactService.addContact(contactRequest);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<ContactWrapper>> getAllContacts(@PathVariable(name = "userId") Long userId) {
        return contactService.getAllContacts(userId);
    }

    @DeleteMapping("/{userId}/delete/{contactId}")
    public void deleteContactById(@PathVariable(name = "userId") Long userId,
                                  @PathVariable(name = "contactId") Long contactId) {
        contactService.deleteContactById(userId, contactId);
    }

}