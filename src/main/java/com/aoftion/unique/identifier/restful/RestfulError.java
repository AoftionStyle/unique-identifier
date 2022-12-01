package com.aoftion.unique.identifier.restful;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(Include.NON_NULL)
public class RestfulError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss.SSS")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String localizedMessage;
    @NotEmpty
    private String path;

    private RestfulError() {
        timestamp = LocalDateTime.now();
    }

    RestfulError(HttpStatus status, String path) {
        this();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.path = path;
    }
 
    RestfulError(HttpStatus status, String path, String message) {
        this();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.path = path;
        this.message = message;
    }
 
    RestfulError(HttpStatus status, String path, String message, Exception ex) {
        this();
        this.status = status.value();
        this.path = path;
        this.message = message;
        this.localizedMessage = ex.getLocalizedMessage();
    }
}
