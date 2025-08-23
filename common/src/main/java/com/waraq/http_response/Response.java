package com.waraq.http_response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private T data;

    private Integer code;

    private Long allRecords;

    private String message;

    private Boolean success = true;

    private String paymentGatewayCode;
}

