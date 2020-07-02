package com.acme.isbnvalidator.web.request;

import com.acme.isbnvalidator.web.dto.IsbnDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class IsbnValidatorAllRequest {
    @NotNull
    @NotEmpty
    private List<IsbnDto> isbnList;
}
