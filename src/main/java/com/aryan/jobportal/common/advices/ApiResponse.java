package com.aryan.jobportal.common.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

@Data
public class ApiResponse<T> {

    private Instant timestamp;
    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timestamp = Instant.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
