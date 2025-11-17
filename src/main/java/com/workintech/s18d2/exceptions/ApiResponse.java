package com.workintech.s18d2.exceptions;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;

    @JsonUnwrapped
    private T data;
}
