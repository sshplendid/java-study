package me.shawn.mono.biz.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
}
