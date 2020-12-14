package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
    private long errorCode;
    private String errorMessage;
}
