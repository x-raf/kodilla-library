package com.kodilla.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyDto {

    private Long id;
    private Long bookTitleId;
    private String bookStatus;
    private List<BookRentDto> bookRents = new ArrayList<>();
}
