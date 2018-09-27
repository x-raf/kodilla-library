package com.kodilla.library.controller;

import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.exception.RentNotFoundException;
import com.kodilla.library.mapper.BookRentMapper;
import com.kodilla.library.service.BookRentDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class BookRentController {

    @Autowired
    private BookRentMapper bookRentMapper;

    @Autowired
    private BookRentDbService bookRentDbService;

    @RequestMapping(method = RequestMethod.GET, value = "/rents")
    public List<BookRentDto> getRents() {
        return bookRentMapper.mapToBookRentDtoList(bookRentDbService.getAllRents());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rents/{rentId}")
    public BookRentDto getRent(@PathVariable Long rentId) throws RentNotFoundException {
        if (bookRentDbService.getRentById(rentId) == null) {
            throw new RentNotFoundException();
        }
        return bookRentMapper.mapToBookRentDto(bookRentDbService.getRentById(rentId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/rents/{rentId}")
    public void deleteRent(@PathVariable Long rentId) {
        bookRentDbService.deleteRent(rentId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/rents")
    public BookRentDto updateRent(@RequestBody BookRentDto bookRentDto) {
        return bookRentMapper.mapToBookRentDto(bookRentDbService.saveRent(bookRentMapper.mapToBookRent(bookRentDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rents", consumes = APPLICATION_JSON_VALUE)
    public void createRent(@RequestBody BookRentDto bookRentDto) {
        bookRentDbService.saveRent(bookRentMapper.mapToBookRent(bookRentDto));
    }
}
