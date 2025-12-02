package com.minibank.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseDto {

    private String statusCode;

    public String getStatusCode() {
        return statusCode;
    }

    public ResponseDto(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    private String statusMsg;

}
