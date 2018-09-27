package com.kodilla.library.controller;

import com.kodilla.library.domain.dto.BookReaderDto;
import com.kodilla.library.exception.ReaderNotFoundException;
import com.kodilla.library.mapper.BookReaderMapper;
import com.kodilla.library.service.BookReaderDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class BookReaderController {

    @Autowired
    BookReaderMapper bookReaderMapper;

    @Autowired
    BookReaderDbService bookReaderDbService;

    @RequestMapping(method = RequestMethod.GET, value = "/readers")
    public List<BookReaderDto> getReaders() {
        return bookReaderMapper.mapToBookReaderDtoList(bookReaderDbService.getAllReaders());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/readers/{readerId}")
    public BookReaderDto getReader(@PathVariable Long readerId) throws ReaderNotFoundException {
        if (bookReaderDbService.getReaderById(readerId) == null) {
            throw new ReaderNotFoundException();
        }
        return bookReaderMapper.mapToBookReaderDto(bookReaderDbService.getReaderById(readerId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/readers/{readerId}")
    public void deleteReader(@PathVariable Long readerId) {
        bookReaderDbService.deleteReader(readerId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/readers")
    public BookReaderDto updateReader(@RequestBody BookReaderDto bookReaderDto) {
        return bookReaderMapper.mapToBookReaderDto(bookReaderDbService.saveReader(bookReaderMapper.mapToBookReader(bookReaderDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/readers", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody BookReaderDto bookReaderDto) {
        bookReaderDbService.saveReader(bookReaderMapper.mapToBookReader(bookReaderDto));
    }
}
