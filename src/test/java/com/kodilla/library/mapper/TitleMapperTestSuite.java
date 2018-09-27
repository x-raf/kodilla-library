package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookReader;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.domain.dto.BookTitleDto;
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
public class TitleMapperTestSuite {

    @Autowired
    private BookTitleMapper bookTitleMapper;

    @Test
    public void testMapToBookTitle() {
        //Given
        List<BookRentDto> rentDtos = new ArrayList<>();
        rentDtos.add(new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookCopyDto> copiesDto = new ArrayList<>();
        copiesDto.add(new BookCopyDto(1L, 1L, "Lost", rentDtos));
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Altered Carbon", "Richard K. Morgan", 2002, copiesDto);
        //When
        BookTitle bookTitle = bookTitleMapper.mapToBookTitle(bookTitleDto);
        //Then
        assertEquals(2002, bookTitle.getPublicationDate());
        assertEquals("Richard K. Morgan", bookTitle.getAuthor());
        assertEquals("Lost", bookTitle.getBookCopies().get(0).getBookStatus());
        assertEquals(LocalDate.of(2018,9,21), bookTitle.getBookCopies().get(0).getBookRents().get(0).getRentStartDate());
    }

    @Test
    public void testMapToBookTitleDto() {
        //Given
        List<BookRent> rents = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        rents.add(new BookRent(1L,bookCopy,bookReader,LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookCopy> copies = new ArrayList<>();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies);
        copies.add(new BookCopy(1L,bookTitle,"Lost",rents));
        //When
        BookTitleDto bookTitleDto = bookTitleMapper.mapToBookTitleDto(bookTitle);
        //Then
        assertEquals(2002, bookTitleDto.getPublicationDate());
        assertEquals("Lost", bookTitleDto.getBookCopies().get(0).getBookStatus());
        assertEquals(LocalDate.of(2018,9,21), bookTitleDto.getBookCopies().get(0).getBookRents().get(0).getRentStartDate());
    }

    @Test
    public void testMapToBookTitleDtoList() {
        //Given
        List<BookRent> rents = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        rents.add(new BookRent(1L,bookCopy,bookReader,LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookCopy> copies = new ArrayList<>();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies);
        copies.add(new BookCopy(1L,bookTitle,"Lost",rents));
        List<BookTitle> bookTitleList = new ArrayList<>();
        bookTitleList.add(new BookTitle(1L,"Altered Carbon", "Richard K. Morgan", 2002, copies));
        bookTitleList.add(new BookTitle(2L,"Altered Carbon", "Richard K. Morgan", 2002, copies));
        bookTitleList.add(new BookTitle(3L,"Altered Carbon", "Richard K. Morgan", 2002, copies));
        //When
        List<BookTitleDto> bookTitleDtoList = bookTitleMapper.mapToBookTitleDtoList(bookTitleList);
        //Then
        assertEquals(3,bookTitleDtoList.size());
    }
}
