package com.kodilla.library.service;

import com.kodilla.library.domain.BookReader;
import com.kodilla.library.repository.BookReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class BookReaderDbService {
    @Autowired
    private BookReaderRepository bookReaderRepository;

    public List<BookReader> getAllReaders() {
        return bookReaderRepository.findAll();
    }

    public BookReader getReaderById(final Long id) {
        return bookReaderRepository.findOne(id);
    }

    public BookReader saveReader(final BookReader bookReader) {
        return bookReaderRepository.save(bookReader);
    }

    public void deleteReader(Long id) {
        bookReaderRepository.delete(id);
    }
}
