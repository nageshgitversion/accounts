package com.minibank.accounts.service;

import com.minibank.accounts.dto.CustomerDto;

public interface IAccountsService {
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchCustomerDetails(String mobileNumber);

    boolean updateCustomerDetails(CustomerDto customerDto);

    public boolean deleteAccount(String mobileNumber);

    public void test();

}
