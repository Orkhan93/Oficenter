package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.request.ContactRequest;
import az.digitalhands.oficenter.response.ContactResponse;
import az.digitalhands.oficenter.response.ContactResponseList;
import az.digitalhands.oficenter.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/add")
    public ResponseEntity<ContactResponse> addContact(@RequestBody ContactRequest contactRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.addContact(contactRequest));
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<ContactResponseList> getAllContacts(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(contactService.getAllContacts(userId));
    }

    @DeleteMapping("/{userId}/delete/{contactId}")
    public void deleteContactById(@PathVariable(name = "userId") Long userId,
                                  @PathVariable(name = "contactId") Long contactId) {
        contactService.deleteContactById(userId, contactId);
    }

}