package com.kodilla.library.controller;

import com.google.gson.Gson;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.service.BookCopyDbService;
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
@WebMvcTest(BookCopyController.class)
public class CopyControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookCopyDbService copyDbService;

    @MockBean
    private BookCopyMapper copyMapper;

    @Test
    public void shouldFetchEmptyCopiesList() throws Exception {
        //Given
        List<BookCopy> bookCopyList = new ArrayList<>();
        List<BookCopyDto> bookCopyDtoList = new ArrayList<>();

        when(copyDbService.getAllBookCopies()).thenReturn(bookCopyList);
        when(copyMapper.mapToBookCopyDtoList(anyList())).thenReturn(bookCopyDtoList);
        //When&Then
        mockMvc.perform(get("/v1/copies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetAllCopies() throws Exception {
        //Given
        List<BookCopy> bookCopyList = new ArrayList<>();
        List<BookRent> bookRents = new ArrayList<>();
        BookTitle bookTitle = new BookTitle(1L, "Altered Carbon", "Richard K. Morgan", 2002, bookCopyList);
        bookCopyList.add(new BookCopy(1L,bookTitle,"Lost",bookRents));
        bookCopyList.add(new BookCopy(2L,bookTitle,"Rented",bookRents));
        bookCopyList.add(new BookCopy(3L,bookTitle,"Available",bookRents));
        List<BookCopyDto> bookCopyDtoList = new ArrayList<>();
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        bookCopyDtoList.add(new BookCopyDto(1L,1L,"Lost",bookRentsDto));
        bookCopyDtoList.add(new BookCopyDto(2L,1L,"Rented",bookRentsDto));
        bookCopyDtoList.add(new BookCopyDto(3L,1L,"Available",bookRentsDto));

        when(copyDbService.getAllBookCopies()).thenReturn(bookCopyList);
        when(copyMapper.mapToBookCopyDtoList(bookCopyList)).thenReturn(bookCopyDtoList);
        //When&Then
        mockMvc.perform(get("/v1/copies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].bookStatus", is("Available")))
                .andExpect(jsonPath("$[1].bookTitleId", is(1)));
    }

    @Test
    public void shouldGetCopy() throws Exception {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        BookTitle bookTitle = new BookTitle();
        BookCopy bookCopy = new BookCopy(1L,bookTitle,"Lost",bookRents);
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        BookCopyDto bookCopyDto = new BookCopyDto(1L,1L,"Lost",bookRentsDto);

        when(copyDbService.getBookCopyById(1L)).thenReturn(bookCopy);
        when(copyMapper.mapToBookCopyDto(bookCopy)).thenReturn(bookCopyDto);
        //When&Then
        mockMvc.perform(get("/v1/copies/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.bookStatus", is("Lost")));
    }

    @Test
    public void shouldDeleteCopy() throws Exception {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        BookTitle bookTitle = new BookTitle();
        BookCopy bookCopy = new BookCopy(1L,bookTitle,"Lost",bookRents);

        when(copyDbService.getBookCopyById(1L)).thenReturn(bookCopy);
        copyDbService.deleteBookCopy(1L);
        //When&Then
        mockMvc.perform(get("/v1/copies/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateCopy() throws Exception {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        BookTitle bookTitle = new BookTitle();
        BookCopy bookCopy = new BookCopy(1L, bookTitle, "Lost", bookRents);
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        BookCopyDto bookCopyDto = new BookCopyDto(1L, 1L, "Lost", bookRentsDto);

        when(copyDbService.saveBookCopy(bookCopy)).thenReturn(bookCopy);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookCopyDto);
        //When&Then
        mockMvc.perform(post("/v1/copies")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateCopy() throws Exception {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        BookTitle bookTitle = new BookTitle();
        BookCopy bookCopy = new BookCopy(1L, bookTitle, "Lost", bookRents);
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        BookCopyDto bookCopyDto = new BookCopyDto(1L, 1L, "Lost", bookRentsDto);

        when(copyDbService.saveBookCopy(copyMapper.mapToBookCopy(bookCopyDto))).thenReturn(bookCopy);
        when(copyMapper.mapToBookCopyDto(bookCopy)).thenReturn(bookCopyDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookCopyDto);
        //When&Then
        mockMvc.perform(post("/v1/copies")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.bookStatus", is("Lost")));
    }
}
