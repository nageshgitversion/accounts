package com.minibank.accounts.controller;


import com.minibank.accounts.constants.AccountConstants;
import com.minibank.accounts.dto.CustomerDto;
import com.minibank.accounts.dto.ErrorResponseDto;
import com.minibank.accounts.dto.ResponseDto;
import com.minibank.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(value = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {


    private IAccountsService iservice;

    public AccountsController(IAccountsService iservice) {
        this.iservice = iservice;
    }

@PostMapping("/create")
public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
    iservice.createAccount(customerDto);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ResponseDto(AccountConstants.STATUS_201,AccountConstants.MESSAGE_201));
}
@GetMapping("/fetch")
public ResponseEntity<CustomerDto> fetchCustomerDetails(@RequestParam String mobileNumber){
        CustomerDto customerDto = iservice.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDto);
}

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iservice.updateCustomerDetails(customerDto);

        if (isUpdated){
            return  ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.STATUS_200));
        }else {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(AccountConstants.STATUS_417,AccountConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@Pattern(regexp = "(^$|[0-9]{10})",message = "mobile number length should be 10") @RequestParam String mobileNumber) {
        boolean isDeleted = iservice.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> getData(@RequestParam String name){

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
