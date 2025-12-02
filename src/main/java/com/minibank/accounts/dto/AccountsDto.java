package com.minibank.accounts.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class AccountsDto {

    private Long accountNumber;

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    private String accountType;

    private String branchAddress;
}
