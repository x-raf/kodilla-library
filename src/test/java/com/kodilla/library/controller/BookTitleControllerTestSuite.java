package com.kodilla.library.controller;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookReader;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.domain.dto.BookTitleDto;
import com.kodilla.library.mapper.BookTitleMapper;
import com.kodilla.library.service.BookTitleDbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookTitleController.class)
public class BookTitleControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookTitleDbService titleDbService;

    @MockBean
    private BookTitleMapper titleMapper;

    @Test
    public void shouldFetchEmptyTitlesList() throws Exception {
        //Given
        List<BookTitle> bookTitleList = new ArrayList<>();
        List<BookTitleDto> bookTitleDtoList = new ArrayList<>();

        when(titleDbService.getAllBookTitles()).thenReturn(bookTitleList);
        when(titleMapper.mapToBookTitleDtoList(anyList())).thenReturn(bookTitleDtoList);
        //When & Then
        mockMvc.perform(get("/v1/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetAllTitles() throws Exception {
        //Given
        List<BookRentDto> rentDtos = new ArrayList<>();
        rentDtos.add(new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookCopyDto> copiesDto = new ArrayList<>();
        copiesDto.add(new BookCopyDto(1L, 1L, "Lost", rentDtos));
        copiesDto.add(new BookCopyDto(2L, 1L, "Rented", rentDtos));
        copiesDto.add(new BookCopyDto(3L, 1L, "Available", rentDtos));

        List<BookRent> rents = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        rents.add(new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookCopy> copies = new ArrayList<>();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies);
        copies.add(new BookCopy(1L, bookTitle, "Lost", rents));
        copies.add(new BookCopy(2L, bookTitle, "Rented", rents));
        copies.add(new BookCopy(3L, bookTitle, "Available", rents));

        List<BookTitle> bookTitleList = new ArrayList<>();
        bookTitleList.add(new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies));
        bookTitleList.add(new BookTitle(2L, "Altered Carbon", "Richard K. Morgan", 2002, copies));
        bookTitleList.add(new BookTitle(3L, "Altered Carbon", "Richard K. Morgan", 2002, copies));

        List<BookTitleDto> bookTitleDtoList = new ArrayList<>();
        bookTitleDtoList.add(new BookTitleDto(1L, "Altered Carbon", "Richard K. Morgan", 2002, copiesDto));
        bookTitleDtoList.add(new BookTitleDto(2L, "Altered Carbon", "Richard K. Morgan", 2002, copiesDto));
        bookTitleDtoList.add(new BookTitleDto(3L, "Altered Carbon", "Richard K. Morgan", 2002, copiesDto));

        when(titleDbService.getAllBookTitles()).thenReturn(bookTitleList);
        when(titleMapper.mapToBookTitleDtoList(bookTitleList)).thenReturn(bookTitleDtoList);

        //When & Then
        mockMvc.perform(get("/v1/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].title", is("Altered Carbon")))
                .andExpect(jsonPath("$[2].author", is("Richard K. Morgan")))
                .andExpect(jsonPath("$[0].bookCopies.[0].bookStatus", is("Lost")));
    }

    @Test
    public void shouldGetTitle() throws Exception {
        //Given
        List<BookRentDto> rentDtos = new ArrayList<>();
        rentDtos.add(new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookRent> rents = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        rents.add(new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookCopy> copies = new ArrayList<>();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies);
        copies.add(new BookCopy(1L, bookTitle, "Lost", rents));
        List<BookCopyDto> copiesDto = new ArrayList<>();
        copiesDto.add(new BookCopyDto(1L, 1L, "Lost", rentDtos));
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Altered Carbon", "Richard K. Morgan", 2002, copiesDto);

        when(titleDbService.getBookTitleById(1L)).thenReturn(bookTitle);
        when(titleMapper.mapToBookTitleDto(bookTitle)).thenReturn(bookTitleDto);
        //When & Then
        mockMvc.perform(get("/v1/books/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Altered Carbon")))
                .andExpect(jsonPath("$.author", is("Richard K. Morgan")));
    }

    @Test
    public void shouldDeleteTitle() throws Exception {
        //Given
        List<BookRent> rents = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        rents.add(new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookCopy> copies = new ArrayList<>();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies);
        copies.add(new BookCopy(1L, bookTitle, "Lost", rents));

        when(titleDbService.getBookTitleById(1L)).thenReturn(bookTitle);
        titleDbService.deleteBookTitle(1L);
        //When&Then
        mockMvc.perform(get("/v1/books/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateTitle() throws Exception {
        //Given
        List<BookRentDto> rentDtos = new ArrayList<>();
        rentDtos.add(new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookRent> rents = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        rents.add(new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookCopy> copies = new ArrayList<>();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies);
        copies.add(new BookCopy(1L, bookTitle, "Lost", rents));
        List<BookCopyDto> copiesDto = new ArrayList<>();
        copiesDto.add(new BookCopyDto(1L, 1L, "Lost", rentDtos));
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Altered Carbon", "Richard K. Morgan", 2002, copiesDto);

        when(titleDbService.saveBookTitle(bookTitle)).thenReturn(bookTitle);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookTitleDto);
        System.out.println(jsonContent);
        //When&Then
        mockMvc.perform(post("/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTitle() throws Exception {
        //Given
        List<BookRentDto> rentDtos = new ArrayList<>();
        rentDtos.add(new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookRent> rents = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        rents.add(new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        List<BookCopy> copies = new ArrayList<>();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, copies);
        copies.add(new BookCopy(1L, bookTitle, "Lost", rents));
        List<BookCopyDto> copiesDto = new ArrayList<>();
        copiesDto.add(new BookCopyDto(1L, 1L, "Lost", rentDtos));
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Altered Carbon", "Richard K. Morgan", 2002, copiesDto);

        when(titleDbService.saveBookTitle(titleMapper.mapToBookTitle(bookTitleDto))).thenReturn(bookTitle);
        when(titleMapper.mapToBookTitleDto(bookTitle)).thenReturn(bookTitleDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookTitleDto);
        //When&Then
        mockMvc.perform(put("/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Altered Carbon")))
                .andExpect(jsonPath("$.author", is("Richard K. Morgan")));
    }
}
