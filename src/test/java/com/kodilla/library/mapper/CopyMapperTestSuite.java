package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookReader;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.dto.BookCopyDto;
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
public class CopyMapperTestSuite {
    @Autowired
    private BookCopyMapper bookCopyMapper;

    @Test
    public void testMapToBookCopy() {
        //Given
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        bookRentsDto.add(new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        BookCopyDto bookCopyDto = new BookCopyDto(1L, 1L, "Rented", bookRentsDto);
        //When
        BookCopy bookCopy = bookCopyMapper.mapToBookCopy(bookCopyDto);
        //Then
        assertEquals(Long.valueOf(1L), bookCopy.getBookTitle().getId());
        }

    @Test
    public void testMapToBookCopyDto() {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        List<BookCopy> copies = new ArrayList<>();
        BookReader bookReader = new BookReader();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, "Lost", bookRents);
        bookRents.add(new BookRent(1L,bookCopy,bookReader,LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        //When
        BookCopyDto bookCopyDto = bookCopyMapper.mapToBookCopyDto(bookCopy);
        //Then
        assertEquals(Long.valueOf(1L), bookCopyDto.getBookTitleId());
        assertEquals("Lost", bookCopyDto.getBookStatus());
        //assertEquals("Altered Carbon", bookCopyDto.);
    }

    @Test
    public void testMapToBookCopyDtoList() {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        List<BookCopy> bookCopyList = new ArrayList<>();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, bookCopyList);
        bookCopyList.add(new BookCopy(1L, bookTitle, "Lost", bookRents));
        bookCopyList.add(new BookCopy(2L, bookTitle, "Rented", bookRents));
        bookCopyList.add(new BookCopy(3L, bookTitle, "Available", bookRents));
        //When
        List<BookCopyDto> bookCopyDtoList = bookCopyMapper.mapToBookCopyDtoList(bookCopyList);
        //Then
        assertEquals(3,bookCopyDtoList.size());
        assertEquals("Rented", bookCopyDtoList.get(1).getBookStatus());
    }
}
