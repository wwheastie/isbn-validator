package com.acme.isbnvalidator.service;

import com.acme.isbnvalidator.model.ValidatedIsbn;
import com.acme.isbnvalidator.service.impl.IsbnValidatorServiceImpl;
import com.acme.isbnvalidator.utilities.ApplicationMessage;
import com.acme.isbnvalidator.web.dto.IsbnDto;
import com.acme.isbnvalidator.web.request.IsbnValidatorAllRequest;
import com.acme.isbnvalidator.web.request.IsbnValidatorAllResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IsbnValidatorServiceImplTest {
    private IsbnValidatorService isbnValidatorService;

    @Before
    public void setUp() {
        isbnValidatorService = new IsbnValidatorServiceImpl();
    }

    @After
    public void tearDown() {
        isbnValidatorService = null;
    }

    @Test(expected = Exception.class)
    public void testValidateAllWithNullRequest() {
        IsbnValidatorAllRequest request = null;
        isbnValidatorService.validateAll(request);
    }

    @Test(expected = Exception.class)
    public void testValidateAllWithNullListInRequest() {
        IsbnValidatorAllRequest request = new IsbnValidatorAllRequest();
        isbnValidatorService.validateAll(request);
    }

    @Test()
    public void testValidateAllWithEmptyListInRequest() {
        IsbnValidatorAllRequest request = new IsbnValidatorAllRequest();
        request.setIsbnList(new ArrayList<IsbnDto>());
        IsbnValidatorAllResponse response = isbnValidatorService.validateAll(request);

        assertEquals(0, response.getValidatedIsbnList().size());
    }

    @Test()
    public void testValidateAllWithEmptyObjectInRequest() {
        IsbnValidatorAllRequest request = new IsbnValidatorAllRequest();
        List<IsbnDto> isbnDtoList = new ArrayList<>();
        isbnDtoList.add(new IsbnDto());
        request.setIsbnList(isbnDtoList);

        IsbnValidatorAllResponse response = isbnValidatorService.validateAll(request);

        assertEquals(1, response.getValidatedIsbnList().size());
        assertEquals(ApplicationMessage.INVALID_REASON_NULL_OR_EMPTY,
                response.getValidatedIsbnList().get(0).getInvalidReason());
    }

    @Test()
    public void testValidateAllWithInvalidCharacters() {
        IsbnValidatorAllRequest request = new IsbnValidatorAllRequest();
        List<IsbnDto> isbnDtoList = new ArrayList<>();
        IsbnDto isbnDto = new IsbnDto();
        isbnDto.setIsbn("ISBN-10: 0-19-852663-6");
        isbnDtoList.add(isbnDto);
        request.setIsbnList(isbnDtoList);

        IsbnValidatorAllResponse response = isbnValidatorService.validateAll(request);

        assertEquals(1, response.getValidatedIsbnList().size());
        assertEquals(ApplicationMessage.INVALID_REASON_INCORRECT_CHARACTERS_OR_FORMAT,
                response.getValidatedIsbnList().get(0).getInvalidReason());
    }

    @Test()
    public void testValidateAllWithInvalidLength() {
        IsbnValidatorAllRequest request = new IsbnValidatorAllRequest();
        List<IsbnDto> isbnDtoList = new ArrayList<>();
        IsbnDto isbnDto = new IsbnDto();
        isbnDto.setIsbn("0-19-852663-667");
        isbnDtoList.add(isbnDto);
        request.setIsbnList(isbnDtoList);

        IsbnValidatorAllResponse response = isbnValidatorService.validateAll(request);

        assertEquals(1, response.getValidatedIsbnList().size());
        assertEquals(ApplicationMessage.INVALID_REASON_INCORRECT_LENGTH,
                response.getValidatedIsbnList().get(0).getInvalidReason());
    }

    @Test()
    public void testValidateAllWithInvalidCheckDigit() {
        IsbnValidatorAllRequest request = new IsbnValidatorAllRequest();
        List<IsbnDto> isbnDtoList = new ArrayList<>();
        IsbnDto isbnDto = new IsbnDto();
        isbnDto.setIsbn("0-19-852663-8");
        isbnDtoList.add(isbnDto);
        request.setIsbnList(isbnDtoList);

        IsbnValidatorAllResponse response = isbnValidatorService.validateAll(request);

        assertEquals(1, response.getValidatedIsbnList().size());
        assertEquals(ApplicationMessage.INVALID_REASON_INCORRECT_CHECK_DIGIT,
                response.getValidatedIsbnList().get(0).getInvalidReason());
    }

    @Test()
    public void testValidateAllWithMultipleISBNS() {
        IsbnValidatorAllRequest request = new IsbnValidatorAllRequest();
        List<IsbnDto> isbnDtoList = new ArrayList<>();

        IsbnDto isbnDto = new IsbnDto();
        isbnDto.setIsbn("0-19-852663-8");
        isbnDtoList.add(isbnDto);

        IsbnDto isbnDto2 = new IsbnDto();
        isbnDto2.setIsbn("978-0-19-852663-6");
        isbnDtoList.add(isbnDto2);

        IsbnDto isbn3 = new IsbnDto();
        isbnDtoList.add(isbn3);

        request.setIsbnList(isbnDtoList);

        IsbnValidatorAllResponse response = isbnValidatorService.validateAll(request);

        assertEquals(3, response.getValidatedIsbnList().size());
        assertFalse(response.getValidatedIsbnList().get(0).isValid());
        assertTrue(response.getValidatedIsbnList().get(1).isValid());
        assertFalse(response.getValidatedIsbnList().get(2).isValid());
    }

    @Test()
    public void testValidISBNTen() {
        String isbn10 = "1 86197 271-7";
        ValidatedIsbn validatedIsbn = isbnValidatorService.validateIsbnTen(isbn10);
        assertTrue(validatedIsbn.isValid());
    }

    @Test()
    public void testValidISBNThirteen() {
        String isbn13 = "978-0-19-852663-6";
        ValidatedIsbn validatedIsbn = isbnValidatorService.validateIsbnThirteen(isbn13);
        assertTrue(validatedIsbn.isValid());
    }
}
