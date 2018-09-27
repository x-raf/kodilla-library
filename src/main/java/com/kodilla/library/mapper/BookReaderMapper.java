package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookReader;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.dto.BookReaderDto;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.service.BookCopyDbService;
import com.kodilla.library.service.BookReaderDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookReaderMapper {

    @Autowired
    private BookCopyDbService bookCopyDbService;

    @Autowired
    private BookReaderDbService bookReaderDbService;

    public BookReader mapToBookReader(final BookReaderDto bookReaderDto) {
        List<BookRent> rents = new ArrayList<>();
        for (BookRentDto bookRentDto : bookReaderDto.getReaderRents()) {
            rents.add(new BookRent(bookRentDto.getId(),
                    bookCopyDbService.getBookCopyById(bookRentDto.getBookCopyId()),
                    bookReaderDbService.getReaderById(bookRentDto.getBookReaderId()),
                    bookRentDto.getRentStartDate(),
                    bookRentDto.getRentEndDate()));
        }
        return new BookReader(
                bookReaderDto.getId(),
                bookReaderDto.getFirstName(),
                bookReaderDto.getLastName(),
                bookReaderDto.getAccountCreatedDate(),
                rents);
    }

    public BookReaderDto mapToBookReaderDto(final BookReader bookReader) {
        List<BookRentDto> rents = new ArrayList<>();
        for (BookRent bookRent : bookReader.getReaderRents()) {
            rents.add(new BookRentDto(bookRent.getId(),
                    bookRent.getBookCopy().getId(),
                    bookRent.getBookReader().getId(),
                    bookRent.getRentStartDate(),
                    bookRent.getRentEndDate()));
        }
        return new BookReaderDto(
                bookReader.getId(),
                bookReader.getFirstName(),
                bookReader.getLastName(),
                bookReader.getAccountCreatedDate(),
                rents);
    }

    public List<BookReaderDto> mapToBookReaderDtoList(final List<BookReader> bookReaderList) {
        return bookReaderList.stream()
                .map(r -> mapToBookReaderDto(r))
                .collect(Collectors.toList());
    }
}
