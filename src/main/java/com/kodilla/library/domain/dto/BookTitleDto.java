package com.kodilla.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookTitleDto {

    private Long id;
    private String title;
    private String author;
    private int publicationDate;

    private List<BookCopyDto> bookCopies = new ArrayList<>();
}
