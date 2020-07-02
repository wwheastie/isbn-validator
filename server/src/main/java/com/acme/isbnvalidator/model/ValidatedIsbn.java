package com.acme.isbnvalidator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatedIsbn {
    private String isbn;
    private boolean valid;
    private String invalidReason;
    private String type;
}
