package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.service.BookCopyDbService;
import com.kodilla.library.service.BookReaderDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookRentMapper {

    @Autowired
    private BookReaderDbService bookReaderDbService;

    @Autowired
    private BookCopyDbService bookCopyDbService;

    public BookRent mapToBookRent(final BookRentDto bookRentDto) {
        return new BookRent(
                bookRentDto.getId(),
                bookCopyDbService.getBookCopyById(bookRentDto.getBookCopyId()),
                bookReaderDbService.getReaderById(bookRentDto.getBookReaderId()),
                bookRentDto.getRentStartDate(),
                bookRentDto.getRentEndDate());
    }

    public BookRentDto mapToBookRentDto(final BookRent bookRent) {
        return new BookRentDto(
                bookRent.getId(),
                bookRent.getBookCopy().getId(),
                bookRent.getBookReader().getId(),
                bookRent.getRentStartDate(),
                bookRent.getRentEndDate());
    }

    public List<BookRentDto> mapToBookRentDtoList(final List<BookRent> bookRentList) {
        return bookRentList.stream()
                .map(rent -> mapToBookRentDto(rent))
                .collect(Collectors.toList());
    }
}
