package com.acme.isbnvalidator.service;

import com.acme.isbnvalidator.model.ValidatedIsbn;
import com.acme.isbnvalidator.web.request.IsbnValidatorAllRequest;
import com.acme.isbnvalidator.web.request.IsbnValidatorAllResponse;
import org.springframework.stereotype.Service;

@Service
public interface IsbnValidatorService {
    ValidatedIsbn validateIsbnTen(String isbn);
    ValidatedIsbn validateIsbnThirteen(String isbn);
    IsbnValidatorAllResponse validateAll(IsbnValidatorAllRequest validatorAllRequest);
}
