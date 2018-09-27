package com.kodilla.library.controller;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.exception.CopyNotFoundException;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.service.BookCopyDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class BookCopyController {

    @Autowired
    private BookCopyMapper bookCopyMapper;

    @Autowired
    private BookCopyDbService bookCopyDbService;

    @RequestMapping(method = RequestMethod.GET, value = "/copies")
    public List<BookCopyDto> getCopies() {
        return bookCopyMapper.mapToBookCopyDtoList(bookCopyDbService.getAllBookCopies());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/copies/{copyId}")
    public BookCopyDto getCopy(@PathVariable Long copyId) throws CopyNotFoundException {
        if (bookCopyDbService.getBookCopyById(copyId) == null) {
            throw new CopyNotFoundException();
        }
        return bookCopyMapper.mapToBookCopyDto(bookCopyDbService.getBookCopyById(copyId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/copies/{copyId}")
    public void deleteCopy(@PathVariable Long copyId) {
        bookCopyDbService.deleteBookCopy(copyId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/copies")
    public BookCopyDto updateCopy(@RequestBody BookCopyDto bookCopyDto) {
        return bookCopyMapper.mapToBookCopyDto(bookCopyDbService.saveBookCopy(bookCopyMapper.mapToBookCopy(bookCopyDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/copies", consumes = APPLICATION_JSON_VALUE)
    public void createCopy(@RequestBody BookCopyDto bookCopyDto) {
        bookCopyDbService.saveBookCopy(bookCopyMapper.mapToBookCopy(bookCopyDto));
    }
}
