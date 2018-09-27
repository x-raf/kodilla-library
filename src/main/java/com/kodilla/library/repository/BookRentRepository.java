package com.kodilla.library.repository;

import com.kodilla.library.domain.BookRent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRentRepository extends CrudRepository<BookRent, Long> {

    @Override
    List<BookRent> findAll();

}
