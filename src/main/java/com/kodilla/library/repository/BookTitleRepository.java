package com.kodilla.library.repository;

import com.kodilla.library.domain.BookTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookTitleRepository extends CrudRepository<BookTitle, Long> {

    @Override
    List<BookTitle> findAll();
}
