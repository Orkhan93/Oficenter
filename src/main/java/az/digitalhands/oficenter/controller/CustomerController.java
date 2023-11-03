package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.domain.Customer;
import az.digitalhands.oficenter.exception.CustomerNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.repository.CustomerRepository;
import az.digitalhands.oficenter.request.ChangePasswordRequest;
import az.digitalhands.oficenter.request.CustomerRequest;
import az.digitalhands.oficenter.request.ForgotPasswordRequest;
import az.digitalhands.oficenter.request.LoginRequest;
import az.digitalhands.oficenter.response.AuthenticationResponse;
import az.digitalhands.oficenter.response.CustomerResponse;
import az.digitalhands.oficenter.service.CustomerService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody CustomerRequest customerRequest) {
        return customerService.signup(customerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String jwt = customerService.login(loginRequest);
        if (jwt == null) {
            return ResponseEntity.status(BAD_REQUEST).build();
        } else {
            Customer customer = customerRepository.findByEmailEqualsIgnoreCase(loginRequest.getEmail()).orElseThrow(
                    () -> new CustomerNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CUSTOMER_NOT_FOUND));
            if (Objects.nonNull(customer)) {
                AuthenticationResponse response = new AuthenticationResponse();
                response.setJwtToken(jwt);
                response.setId(customer.getId());
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.USER_NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.updateCustomer(customerRequest);
    }

    @PutMapping("/changePassword/{customerId}")
    public ResponseEntity<CustomerResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                                           @PathVariable(name = "customerId") Long customerId) {
        return customerService.changePassword(changePasswordRequest, customerId);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest)
            throws MessagingException {
        return customerService.forgotPassword(forgotPasswordRequest);
    }

    @PostMapping("/checkCustomerToken") //
    public ResponseEntity<?> checkCustomerToken(String token) {
        return customerService.checkCustomerToken(token);
    }

}