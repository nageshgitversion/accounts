package com.minibank.accounts.service.Impl;

import com.minibank.accounts.constants.AccountConstants;
import com.minibank.accounts.dto.AccountsDto;
import com.minibank.accounts.dto.CustomerDto;
import com.minibank.accounts.entity.Accounts;
import com.minibank.accounts.entity.Customer;
import com.minibank.accounts.exception.CustomerAlreadyExistException;
import com.minibank.accounts.exception.handledResourceNotFoundException;
import com.minibank.accounts.mapper.AccountsMapper;
import com.minibank.accounts.mapper.CustomerMapper;
import com.minibank.accounts.repository.AccountsRepository;
import com.minibank.accounts.repository.CustomerRepository;
import com.minibank.accounts.service.IAccountsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository, CustomerRepository customerRepository) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        System.out.println("hello every");
    }

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> customerByMobileNumber = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(customerByMobileNumber.isPresent()){
            throw new CustomerAlreadyExistException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("User");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
        System.out.println("hello every");

    }


    private Accounts createNewAccount(Customer customer){

        Accounts accounts = new Accounts();
        long  longAccountNumber = 1000000000L + new Random().nextInt(900000000);
        accounts.setAccountNumber(longAccountNumber);
        accounts.setCustomerId(customer.getCustomerId());
        accounts.setAccountType(AccountConstants.SAVINGS);
        accounts.setBranchAddress(AccountConstants.ADDRESS);
        accounts.setCreatedAt(LocalDateTime.now());
        accounts.setCreatedBy("User");
        return accounts;
    }

    @Override
    public CustomerDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new handledResourceNotFoundException("customer","mobileNumber",mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(()->new handledResourceNotFoundException("Account","customerId",customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateCustomerDetails(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();

        if(accountsDto!=null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(() -> new handledResourceNotFoundException("account", "accountNumber", accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);
            Long customerId = accounts.getCustomerId();

            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new handledResourceNotFoundException("customer", "customerId", customerId.toString()));
            customerRepository.save(customer);

            isUpdated=true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new handledResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
