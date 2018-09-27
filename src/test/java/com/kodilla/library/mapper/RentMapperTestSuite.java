package com.kodilla.library.mapper;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookReader;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.service.BookReaderDbService;
import com.kodilla.library.service.BookRentDbService;
import org.junit.Assert;
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
public class RentMapperTestSuite {

    @Autowired
    private BookReaderDbService readerDbService;

    @Autowired
    private BookRentMapper bookRentMapper;

    @Test
    public void testMapToBookRent() {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        List<BookCopy> copies = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader(1L,"John","Helena",LocalDate.of(2018,1,1), bookRents);
        bookRents.add(new BookRent(1L,bookCopy,bookReader,LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies);
        copies.add(new BookCopy(1L,bookTitle,"Lost",bookRents));
        BookRentDto bookRentDto = new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30));
        //When
        //readerDbService.saveReader(bookReader);
        BookRent bookRent = bookRentMapper.mapToBookRent(bookRentDto);
        //Then
        //assertEquals(1L, bookRent.getBookReader().getId());
        assertEquals(LocalDate.of(2018,9,30), bookRent.getRentEndDate());
        assertEquals("1", bookRent.getBookReader().getId());
    }
    @Test
    public void testMapToBookRentDto() {
        //Given
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        BookRent bookRent = new BookRent(1L,bookCopy,bookReader,LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30));
        //When
        BookRentDto bookRentDto = bookRentMapper.mapToBookRentDto(bookRent);
        //Then
        assertEquals(LocalDate.of(2018,9,30), bookRentDto.getRentEndDate());
    }
    @Test
    public void testMapToBookRentDtoList() {
        //Given
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        List<BookRent> bookRentList = new ArrayList<>();
        bookRentList.add(new BookRent(1L,bookCopy,bookReader,LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        bookRentList.add(new BookRent(2L,bookCopy,bookReader,LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        bookRentList.add(new BookRent(3L,bookCopy,bookReader,LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        //When
        List<BookRentDto> bookRentDtoList = bookRentMapper.mapToBookRentDtoList(bookRentList);
        //Then
        assertEquals(3,bookRentDtoList.size());
    }
}
