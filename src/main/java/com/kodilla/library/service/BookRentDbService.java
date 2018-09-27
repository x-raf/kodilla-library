package com.kodilla.library.service;

import com.kodilla.library.domain.BookRent;
import com.kodilla.library.repository.BookRentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class BookRentDbService {

    @Autowired
    private BookRentRepository bookRentRepository;

    public List<BookRent> getAllRents() {
        return bookRentRepository.findAll();
    }

    public BookRent getRentById(final Long id) {
        return bookRentRepository.findOne(id);
    }

    public BookRent saveRent(final BookRent bookRent) {
        return bookRentRepository.save(bookRent);
    }

    public void deleteRent(Long id) {
        bookRentRepository.delete(id);
    }
}
