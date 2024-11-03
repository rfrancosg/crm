package com.rfrancos.crm.dto.exception;

import com.rfrancos.crm.utils.DateUtils;
import lombok.Data;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
    private String timestamp;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = DateUtils.getDateFromTimestamp(System.currentTimeMillis());
    }

}