package com.acme.isbnvalidator.service.impl;

import com.acme.isbnvalidator.model.ValidatedIsbn;
import com.acme.isbnvalidator.service.IsbnValidatorService;
import com.acme.isbnvalidator.utilities.ApplicationMessage;
import com.acme.isbnvalidator.web.dto.IsbnDto;
import com.acme.isbnvalidator.web.request.IsbnValidatorAllRequest;
import com.acme.isbnvalidator.web.request.IsbnValidatorAllResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IsbnValidatorServiceImpl implements IsbnValidatorService {
    private static final char[] ALLOWED_CHARACTERS_ARRAY = new char[]{' ', '-'};
    private static final String ISBN_10 = "ISBN-10";
    private static final int ISBN_10_LENGTH = 13;
    private static final String ISBN_13 = "ISBN-13";
    private static final int ISBN_13_LENGTH = 17;
    private static final int[] VALID_ISBN_LENGTHS = new int[]{ISBN_10_LENGTH, ISBN_13_LENGTH};

    @Override
    public ValidatedIsbn validateIsbnTen(String isbn) {
        //Check if isbn is valid and create validated isbn object
        ValidatedIsbn validatedIsbn = createValidatedIsbn(isbn, ISBN_10_LENGTH);

        return validatedIsbn;
    }

    @Override
    public ValidatedIsbn validateIsbnThirteen(String isbn) {
        //Check if isbn is valid and create validated isbn object
        ValidatedIsbn validatedIsbn = createValidatedIsbn(isbn, ISBN_13_LENGTH);

        return validatedIsbn;
    }

    @Override
    public IsbnValidatorAllResponse validateAll(IsbnValidatorAllRequest validatorAllRequest) {
        List<ValidatedIsbn> validatedIsbnList = new ArrayList<>();
        for(IsbnDto isbnDto : validatorAllRequest.getIsbnList()) {
            if(isbnDto != null) {
                validatedIsbnList.add(createValidatedIsbn(isbnDto.getIsbn(),null));
            }
        }
        return new IsbnValidatorAllResponse(validatedIsbnList);
    }

    private ValidatedIsbn createValidatedIsbn(String isbn, Integer expectedIsbnLength) {
        //Local variables
        boolean valid = false;
        String invalidReason = "";
        String type = "";

        //Check if isbn is not null or empty, has any invalid characters, invalid length, and if check digit is valid
        if(StringUtils.isEmpty(isbn)) {
            invalidReason = ApplicationMessage.INVALID_REASON_NULL_OR_EMPTY;
        } else if (!validCharacters(isbn)) {
            invalidReason = ApplicationMessage.INVALID_REASON_INCORRECT_CHARACTERS_OR_FORMAT;
        } else if (!validLength(isbn, expectedIsbnLength)) {
            invalidReason = ApplicationMessage.INVALID_REASON_INCORRECT_LENGTH;
        } else {
            type = getIsbnType(isbn.length());
            valid = validCheckDigit(isbn, type);
            if (!valid) {
                invalidReason = ApplicationMessage.INVALID_REASON_INCORRECT_CHECK_DIGIT;
            }
        }
        return new ValidatedIsbn(isbn, valid, invalidReason, type);
    }

    private boolean validCharacters(String isbn) {
        //Check if the first and last characters are digits
        if(!Character.isDigit(isbn.charAt(0)) ||
                !Character.isDigit(isbn.charAt(isbn.length() - 1))) {
            return false;
        }

        //Check the rest of the characters to make sure they are valid
        for(int i = 1; i < isbn.length() - 1; i++) {
            char character = isbn.charAt(i);
            if(!isAllowedCharacter(character)) {
                return false;
            }
        }

        return true;
    }

    private boolean isAllowedCharacter(char character) {
        if(Character.isDigit(character)) {
            return true;
        } else {
            for(int i = 0; i < ALLOWED_CHARACTERS_ARRAY.length; i++) {
                if (character == ALLOWED_CHARACTERS_ARRAY[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean validLength(String isbn, Integer isbnLength) {
        if(isbnLength != null) {
            return isbn.length() == isbnLength;
        } else {
            for(int i = 0; i < VALID_ISBN_LENGTHS.length; i++) {
                if(isbn.length() == VALID_ISBN_LENGTHS[i]) {
                    return true;
                }
            }
            return false;
        }
    }

    private String getIsbnType(int isbnLength) {
        switch (isbnLength) {
            case ISBN_10_LENGTH:
                return ISBN_10;
            case ISBN_13_LENGTH:
                return ISBN_13;
            default:
                return "";
        }
    }

    private boolean validCheckDigit(String isbn, String isbnType) {
        switch (isbnType) {
            case ISBN_10:
                return ISBNValidator.getInstance().isValidISBN10(isbn);
            case ISBN_13:
                return ISBNValidator.getInstance().isValidISBN13(isbn);
            default:
                return false;
        }
    }
}
