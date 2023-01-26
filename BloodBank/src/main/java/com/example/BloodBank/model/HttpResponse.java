package com.example.BloodBank.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;


@Data
@SuperBuilder
//@JsonInclude(NON_DEFAULT);
public class HttpResponse implements Serializable {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected Map<?, ?> data;

}
