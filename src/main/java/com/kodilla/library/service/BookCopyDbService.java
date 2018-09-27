package com.kodilla.library.service;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class BookCopyDbService {
    @Autowired
    private BookCopyRepository bookCopyRepository;

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }
    public BookCopy getBookCopyById(Long bookCopyId) {
        return bookCopyRepository.findOne(bookCopyId);
    }

    public BookCopy saveBookCopy(final BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    public void deleteBookCopy(Long bookCopyId) {
        bookCopyRepository.delete(bookCopyId);
    }
}
