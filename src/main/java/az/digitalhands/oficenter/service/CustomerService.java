package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.constant.OficenterConstant;
import az.digitalhands.oficenter.domain.Customer;
import az.digitalhands.oficenter.exception.CustomerNotFoundException;
import az.digitalhands.oficenter.exception.IncorrectPasswordException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.CustomerMapper;
import az.digitalhands.oficenter.repository.CustomerRepository;
import az.digitalhands.oficenter.request.ChangePasswordRequest;
import az.digitalhands.oficenter.request.CustomerRequest;
import az.digitalhands.oficenter.request.ForgotPasswordRequest;
import az.digitalhands.oficenter.request.LoginRequest;
import az.digitalhands.oficenter.response.CustomerResponse;
import az.digitalhands.oficenter.security.JwtUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static az.digitalhands.oficenter.constant.OficenterConstant.BY_OFICENTER;
import static az.digitalhands.oficenter.constant.OficenterConstant.USER_ALREADY_EXISTS;
import static az.digitalhands.oficenter.exception.error.ErrorMessage.BAD_CREDENTIALS;
import static az.digitalhands.oficenter.exception.error.ErrorMessage.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public ResponseEntity<?> signup(CustomerRequest customerRequest) {
        if (validationSignUp(customerRequest)) {
            Optional<Customer> customer = customerRepository.findByEmailEqualsIgnoreCase(customerRequest.getEmail());
            if (customer.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(customerMapper.fromModelToResponse
                                (customerRepository.save(customerMapper.fromRequestToModel(customerRequest))));
            } else
                log.error("customerRequest {}", customerRequest);
            return ResponseEntity.status(BAD_REQUEST).body(USER_ALREADY_EXISTS);
        }
        return ResponseEntity.status(BAD_REQUEST).build();
    }

    public String login(LoginRequest loginRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(loginRequest.getEmail());
        if (optionalCustomer.isPresent()) {
            log.info("Inside login {}", optionalCustomer);
            return jwtUtil.generateTokenTest(loginRequest.getEmail());
        }
        log.error("login {}", optionalCustomer);
        return BAD_CREDENTIALS;
    }

    public ResponseEntity<CustomerResponse> updateCustomer(CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerRequest.getId())
                .orElseThrow(() -> new CustomerNotFoundException(HttpStatus.NOT_FOUND.name(), USER_NOT_FOUND));
        if (Objects.nonNull(customer)) {
            return ResponseEntity.status(OK)
                    .body(customerMapper.fromModelToResponse
                            (customerRepository.save(customerMapper.fromRequestToModel(customerRequest))));
        }
        return ResponseEntity.status(BAD_REQUEST).build();
    }

    public ResponseEntity<CustomerResponse> changePassword(ChangePasswordRequest changePasswordRequest, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CUSTOMER_NOT_FOUND + customerId));
        if (!customer.getPassword().equals(changePasswordRequest.getOldPassword())) {
            throw new IncorrectPasswordException(BAD_REQUEST.name(), ErrorMessage.INCORRECT_PASSWORD);
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new IncorrectPasswordException(BAD_REQUEST.name(), ErrorMessage.NOT_MATCHES);
        }
        customer.setPassword(changePasswordRequest.getNewPassword());
        return ResponseEntity.status(OK)
                .body(customerMapper.fromModelToResponse(customerRepository.save(customer)));
    }

    public ResponseEntity<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(forgotPasswordRequest.getEmail());
        if (optionalCustomer.isPresent()) {
            emailService.forgetMail(optionalCustomer.get().getEmail(), BY_OFICENTER, optionalCustomer.get().getPassword());
            return ResponseEntity.status(OK).body(OficenterConstant.CHECK_EMAIL);
        } else
            return ResponseEntity.status(BAD_REQUEST).body(ErrorMessage.USER_NOT_FOUND);
    }

    private boolean validationSignUp(CustomerRequest customerRequest) {
        return customerRequest.getUsername() != null && customerRequest.getEmail() != null
                && customerRequest.getPhone() != null && customerRequest.getPassword() != null;
    }

}