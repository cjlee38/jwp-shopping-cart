package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.EmailDuplicationResponse;
import woowacourse.shoppingcart.application.dto.UserNameDuplicationResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.BcryptPasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.PasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.exception.domain.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.domain.DuplicateCustomerException;
import woowacourse.shoppingcart.ui.dto.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.FindCustomerRequest;
import woowacourse.shoppingcart.ui.dto.UpdateCustomerRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    private final PasswordEncryptor encryptor;
    private final CustomerDao customerDao;

    public CustomerService(PasswordEncryptor encryptor, CustomerDao customerDao) {
        this.encryptor = encryptor;
        this.customerDao = customerDao;
    }

    public Long createCustomer(CustomerRequest request) {
        Customer customer = Customer.fromInput(
            request.getName(),
            request.getPassword(),
            request.getEmail(),
            request.getAddress(),
            request.getPhoneNumber()
        ).encryptPassword(encryptor);

        return customerDao.save(customer)
            .orElseThrow(DuplicateCustomerException::new);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomer(FindCustomerRequest findCustomerRequest) {
        Customer customer = customerDao.findById(findCustomerRequest.getId())
            .orElseThrow(CustomerNotFoundException::new);
        return CustomerResponse.from(customer);
    }

    public void updateCustomer(FindCustomerRequest findCustomerRequest, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerDao.findById(findCustomerRequest.getId())
            .orElseThrow(CustomerNotFoundException::new);
        Customer updatedCustomer = customer.update(updateCustomerRequest.getAddress(),
            updateCustomerRequest.getPhoneNumber());
        customerDao.update(updatedCustomer);
    }

    public void deleteCustomer(FindCustomerRequest findCustomerRequest) {
        customerDao.deleteById(findCustomerRequest.getId());
    }

    public UserNameDuplicationResponse isUserNameDuplicated(String username) {
        return new UserNameDuplicationResponse(username,
            customerDao.isDuplicated(CustomerDao.COLUMN_USERNAME, new UserName(username)));
    }

    public EmailDuplicationResponse isEmailDuplicated(String email) {
        return new EmailDuplicationResponse(email,
            customerDao.isDuplicated(CustomerDao.COLUMN_EMAIL, new Email(email)));
    }
}
