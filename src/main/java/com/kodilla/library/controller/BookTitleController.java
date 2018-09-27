package com.kodilla.library.controller;

import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.dto.BookTitleDto;
import com.kodilla.library.exception.BookNotFoundException;
import com.kodilla.library.mapper.BookTitleMapper;
import com.kodilla.library.service.BookTitleDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class BookTitleController {

    @Autowired
    private BookTitleMapper bookTitleMapper;

    @Autowired
    private BookTitleDbService bookTitleDbService;

    @RequestMapping(method = RequestMethod.GET, value = "/books")
    public List<BookTitleDto> getBookTitles() {
        return bookTitleMapper.mapToBookTitleDtoList(bookTitleDbService.getAllBookTitles());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/books/{bookId}")
    public BookTitleDto getBookTitle(@PathVariable Long bookId) throws BookNotFoundException {
        if (bookTitleDbService.getBookTitleById(bookId) == null) {
            throw new BookNotFoundException();
        }
        return bookTitleMapper.mapToBookTitleDto(bookTitleDbService.getBookTitleById(bookId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/books/{bookId}")
    public void deleteBookTitle(@PathVariable Long bookId) {
        bookTitleDbService.deleteBookTitle(bookId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/books")
    public BookTitleDto updateBookTitle(@RequestBody BookTitleDto bookTitleDto) {
        return bookTitleMapper.mapToBookTitleDto(bookTitleDbService.saveBookTitle(bookTitleMapper.mapToBookTitle(bookTitleDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/books", consumes = APPLICATION_JSON_VALUE)
    public void createBookTitle(@RequestBody BookTitleDto bookTitleDto) {
        bookTitleDbService.saveBookTitle(bookTitleMapper.mapToBookTitle(bookTitleDto));
    }
}
