package com.acme.isbnvalidator.web.dto;

import lombok.Data;

@Data
public class IsbnDto {
    private String isbn;

    public void setIsbn(String isbn) {
        this.isbn = isbn.trim();
    }
}
