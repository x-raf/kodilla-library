package com.kodilla.library.repository;

import com.kodilla.library.domain.BookReader;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookReaderRepository extends CrudRepository<BookReader, Long> {

    @Override
    List<BookReader> findAll();
}
