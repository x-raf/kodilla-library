package com.kodilla.library.controller;

import com.google.gson.Gson;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookReader;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.mapper.BookRentMapper;
import com.kodilla.library.service.BookRentDbService;
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
@WebMvcTest(BookRentController.class)
public class RentControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRentDbService rentDbService;

    @MockBean
    private BookRentMapper rentMapper;

    @Test
    public void shouldFetchEmptyRentsList() throws Exception {
        //Given
        List<BookRent> rentList = new ArrayList<>();
        List<BookRentDto> rentDtoList = new ArrayList<>();

        when(rentDbService.getAllRents()).thenReturn(rentList);
        when(rentMapper.mapToBookRentDtoList(anyList())).thenReturn(rentDtoList);
        //When&Then
        mockMvc.perform(get("/v1/rents").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetAllRents() throws Exception {
        //Given
        List<BookRent> rentList = new ArrayList<>();
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        rentList.add(new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        rentList.add(new BookRent(2L, bookCopy, bookReader, LocalDate.of(2018, 8, 21), LocalDate.of(2018, 8, 30)));
        rentList.add(new BookRent(3L, bookCopy, bookReader, LocalDate.of(2018, 7, 21), LocalDate.of(2018, 7, 30)));
        List<BookRentDto> rentDtoList = new ArrayList<>();
        rentDtoList.add(new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30)));
        rentDtoList.add(new BookRentDto(2L, 2L, 2L, LocalDate.of(2018, 8, 21), LocalDate.of(2018, 8, 30)));
        rentDtoList.add(new BookRentDto(3L, 3L, 3L, LocalDate.of(2018, 7, 21), LocalDate.of(2018, 7, 30)));

        when(rentDbService.getAllRents()).thenReturn(rentList);
        when(rentMapper.mapToBookRentDtoList(rentList)).thenReturn(rentDtoList);
        //When&Then
        mockMvc.perform(get("/v1/rents").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].rentStartDate", is("2018-08-21")));
    }

    @Test
    public void shouldGetRent() throws Exception {
        //Given
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        BookRent bookRent = new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30));
        BookRentDto bookRentDto = new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30));

        when(rentDbService.getRentById(1L)).thenReturn(bookRent);
        when(rentMapper.mapToBookRentDto(bookRent)).thenReturn(bookRentDto);
        //When&Then
        mockMvc.perform(get("/v1/rents/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rentStartDate", is("2018-09-21")));
    }

    @Test
    public void shouldDeleteRent() throws Exception {
        //Given
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        BookRent bookRent = new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30));

        when(rentDbService.getRentById(1L)).thenReturn(bookRent);
        rentDbService.deleteRent(1L);
        //When&Then
        mockMvc.perform(get("/v1/rents/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateRent() throws Exception {
        //Given
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        BookRent bookRent = new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30));
        BookRentDto bookRentDto = new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30));

        when(rentDbService.saveRent(bookRent)).thenReturn(bookRent);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookRentDto);
        //When&Then
        mockMvc.perform(post("/v1/rents")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateRent() throws Exception {
        //Given
        BookCopy bookCopy = new BookCopy();
        BookReader bookReader = new BookReader();
        BookRent bookRent = new BookRent(1L, bookCopy, bookReader, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30));
        BookRentDto bookRentDto = new BookRentDto(1L, 1L, 1L, LocalDate.of(2018, 9, 21), LocalDate.of(2018, 9, 30));

        when(rentDbService.saveRent(rentMapper.mapToBookRent(bookRentDto))).thenReturn(bookRent);
        when(rentMapper.mapToBookRentDto(bookRent)).thenReturn(bookRentDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookRentDto);
        //When&Then
        mockMvc.perform(post("/v1/rents")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rentStartDate", is("2018-09-21")));
    }
}
