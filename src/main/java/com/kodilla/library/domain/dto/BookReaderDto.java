package com.kodilla.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookReaderDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate accountCreatedDate;
    private List<BookRentDto> readerRents = new ArrayList<>();
}
