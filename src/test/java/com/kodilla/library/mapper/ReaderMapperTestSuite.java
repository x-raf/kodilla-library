package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookReader;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.dto.BookReaderDto;
import com.kodilla.library.domain.dto.BookRentDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReaderMapperTestSuite {

    @Autowired
    private BookReaderMapper bookReaderMapper;

    @Test
    public void testMapToBookReader() {
        //Given
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        BookReaderDto bookReaderDto = new BookReaderDto(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRentsDto);
        bookRentsDto.add(new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        //When
        BookReader bookReader = bookReaderMapper.mapToBookReader(bookReaderDto);
        //Then
        assertEquals("Helena", bookReader.getLastName());
        assertEquals(Long.valueOf(1L), bookReader.getId());
    }

    @Test
    public void testMapToBookReaderDto() {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRents);
        bookRents.add(new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        //When
        BookReaderDto bookReaderDto = bookReaderMapper.mapToBookReaderDto(bookReader);
        //Then
        assertEquals("John", bookReaderDto.getFirstName());
        assertEquals(LocalDate.of(2018, 1, 1), bookReaderDto.getAccountCreatedDate());
    }

    @Test
    public void testMapToBookReaderDtoList() {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        List<BookReader> bookReaderList = new ArrayList<>();
        bookReaderList.add(new BookReader(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRents));
        bookReaderList.add(new BookReader(2L, "Joe", "Doe", LocalDate.of(2017, 2, 1), bookRents));
        bookReaderList.add(new BookReader(3L, "Maggy", "Smith", LocalDate.of(2016, 3, 1), bookRents));
        //When
        List<BookReaderDto> bookReaderDtoList = bookReaderMapper.mapToBookReaderDtoList(bookReaderList);
        //Then
        assertEquals(3, bookReaderDtoList.size());
        assertEquals("Joe", bookReaderDtoList.get(1).getFirstName());
        assertEquals(LocalDate.of(2016, 3, 1), bookReaderDtoList.get(2).getAccountCreatedDate());
    }
}
