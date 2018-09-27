package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.service.BookCopyDbService;
import com.kodilla.library.service.BookReaderDbService;
import com.kodilla.library.service.BookTitleDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class BookCopyMapper {
    @Autowired
    private BookTitleDbService bookTitleDbService;

    @Autowired
    private BookCopyDbService bookCopyDbService;

    @Autowired
    private BookReaderDbService bookReaderDbService;

    public BookCopy mapToBookCopy(final BookCopyDto bookCopyDto) {
        List<BookRent> rents = new ArrayList<>();
        for (BookRentDto bookRentDto : bookCopyDto.getBookRents()) {
            rents.add(new BookRent(bookRentDto.getId(),
                    bookCopyDbService.getBookCopyById(bookRentDto.getBookCopyId()),
                    bookReaderDbService.getReaderById(bookRentDto.getBookReaderId()),
                    bookRentDto.getRentStartDate(),
                    bookRentDto.getRentEndDate()));
        }
        return new BookCopy(
                bookCopyDto.getId(),
                bookTitleDbService.getBookTitleById(bookCopyDto.getBookTitleId()),
                bookCopyDto.getBookStatus(),
                rents);
    }

    public BookCopyDto mapToBookCopyDto(final BookCopy bookCopy) {
        List<BookRentDto> rents = new ArrayList<>();
        for (BookRent bookRent : bookCopy.getBookRents()) {
            rents.add(new BookRentDto(bookRent.getId(),
                    bookRent.getBookCopy().getId(),
                    bookRent.getBookReader().getId(),
                    bookRent.getRentStartDate(),
                    bookRent.getRentEndDate()));
        }
        return new BookCopyDto(
                bookCopy.getId(),
                bookCopy.getBookTitle().getId(),
                bookCopy.getBookStatus(),
                rents);
    }

    public List<BookCopyDto> mapToBookCopyDtoList(final List<BookCopy> bookCopyList) {
        return bookCopyList.stream()
                .map(c -> mapToBookCopyDto(c))
                .collect(Collectors.toList());
    }
}
