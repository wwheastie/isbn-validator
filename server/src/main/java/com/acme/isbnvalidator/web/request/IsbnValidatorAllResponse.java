package com.acme.isbnvalidator.web.request;

import com.acme.isbnvalidator.model.ValidatedIsbn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsbnValidatorAllResponse {
    private List<ValidatedIsbn> validatedIsbnList;
}
