package com.kodilla.library.service;

import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.repository.BookTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class BookTitleDbService {
    @Autowired
    private BookTitleRepository bookTitleRepository;

    public List<BookTitle> getAllBookTitles() {
        return bookTitleRepository.findAll();
    }

    public BookTitle getBookTitleById(final Long id) {
        return bookTitleRepository.findOne(id);
    }

    public BookTitle saveBookTitle(final BookTitle bookTitle) {
        return bookTitleRepository.save(bookTitle);
    }

    public void deleteBookTitle(Long id) {
        bookTitleRepository.delete(id);
    }
}
