package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.domain.dto.BookTitleDto;
import com.kodilla.library.service.BookCopyDbService;
import com.kodilla.library.service.BookReaderDbService;
import com.kodilla.library.service.BookTitleDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookTitleMapper {
    @Autowired
    private BookTitleDbService bookTitleDbService;

    @Autowired
    private BookCopyDbService bookCopyDbService;

    @Autowired
    private BookReaderDbService bookReaderDbService;

    public BookTitle mapToBookTitle(final BookTitleDto bookTitleDto) {
        List<BookCopy> copies = new ArrayList<>();
        for(BookCopyDto bookCopyDto:bookTitleDto.getBookCopies()){
            List<BookRent> rents = new ArrayList<>();
            for(BookRentDto bookRentDto : bookCopyDto.getBookRents()){
                rents.add(new BookRent(bookRentDto.getId(),
                        bookCopyDbService.getBookCopyById(bookRentDto.getBookCopyId()),
                        bookReaderDbService.getReaderById(bookRentDto.getBookReaderId()),
                        bookRentDto.getRentStartDate(),
                        bookRentDto.getRentEndDate()));
            }
            copies.add(new BookCopy(bookCopyDto.getId(),bookTitleDbService.getBookTitleById(bookCopyDto.getBookTitleId()),
                            bookCopyDto.getBookStatus(), rents));
        }
        return new BookTitle(
                bookTitleDto.getId(),
                bookTitleDto.getTitle(),
                bookTitleDto.getAuthor(),
                bookTitleDto.getPublicationDate(),
                copies);
    }
    public BookTitleDto mapToBookTitleDto(final BookTitle bookTitle) {
        List<BookCopyDto> bookCopies = new ArrayList<>();
        for(BookCopy bookCopy : bookTitle.getBookCopies()) {
            List<BookRentDto> bookRents = new ArrayList<>();
            for (BookRent bookRent : bookCopy.getBookRents()){
                bookRents.add(new BookRentDto(bookRent.getId(),
                        bookRent.getBookCopy().getId(),
                        bookRent.getBookReader().getId(),
                        bookRent.getRentStartDate(),
                        bookRent.getRentEndDate()));
            }
            bookCopies.add(new BookCopyDto(bookCopy.getId(),
                    bookCopy.getBookTitle().getId(),
                    bookCopy.getBookStatus(),
                    bookRents));
        }
        return new BookTitleDto(
                bookTitle.getId(),
                bookTitle.getTitle(),
                bookTitle.getAuthor(),
                bookTitle.getPublicationDate(),
                bookCopies);
    }

    public List<BookTitleDto> mapToBookTitleDtoList(final List<BookTitle> bookTitleList) {
        return bookTitleList.stream()
                .map(b -> mapToBookTitleDto(b))
                .collect(Collectors.toList());
    }
}
