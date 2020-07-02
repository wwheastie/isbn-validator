package com.acme.isbnvalidator.web.controller;

import com.acme.isbnvalidator.model.ValidatedIsbn;
import com.acme.isbnvalidator.service.IsbnValidatorService;
import com.acme.isbnvalidator.web.request.IsbnValidatorAllRequest;
import com.acme.isbnvalidator.web.request.IsbnValidatorAllResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/validate")
public class IsbnValidatorController {
    @Autowired
    IsbnValidatorService isbnValidatorService;

    @GetMapping("/ten/{isbn}")
    public ResponseEntity<?> validateIsbnTen(@PathVariable("isbn") String isbn) {
        ValidatedIsbn validatedIsbn = isbnValidatorService.validateIsbnTen(isbn);
        return ResponseEntity.ok().body(validatedIsbn);
    }

    @GetMapping("/thirteen/{isbn}")
    public ResponseEntity<?> validateIsbnThirteen(@PathVariable("isbn") String isbn) {
        ValidatedIsbn validatedIsbn = isbnValidatorService.validateIsbnThirteen(isbn);
        return ResponseEntity.ok().body(validatedIsbn);
    }

    @CrossOrigin
    @PostMapping("/all")
    public ResponseEntity<?> validateAll(@Valid @RequestBody IsbnValidatorAllRequest isbnValidatorAllRequest) {
        IsbnValidatorAllResponse response = isbnValidatorService.validateAll(isbnValidatorAllRequest);
        return ResponseEntity.ok().body(response);
    }
}
