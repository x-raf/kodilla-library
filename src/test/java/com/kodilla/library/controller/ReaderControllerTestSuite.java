package com.kodilla.library.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodilla.library.adapter.LocalDateAdapter;
import com.kodilla.library.domain.BookReader;
import com.kodilla.library.domain.BookRent;
import com.kodilla.library.domain.dto.BookReaderDto;
import com.kodilla.library.domain.dto.BookRentDto;
import com.kodilla.library.mapper.BookReaderMapper;
import com.kodilla.library.service.BookReaderDbService;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookReaderController.class)
public class ReaderControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookReaderDbService readerDbService;

    @MockBean
    private BookReaderMapper readerMapper;

    @Test
    public void shouldFetchEmptyReadersList() throws Exception {
        //Given
        List<BookReader> readerList = new ArrayList<>();
        List<BookReaderDto> readerDtoList = new ArrayList<>();

        when(readerDbService.getAllReaders()).thenReturn(readerList);
        when(readerMapper.mapToBookReaderDtoList(anyList())).thenReturn(readerDtoList);
        //When&Then
        mockMvc.perform(get("/v1/readers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetAllReaders() throws Exception {
        //Given
        List<BookReader> readerList = new ArrayList<>();
        List<BookRent> bookRents = new ArrayList<>();
        readerList.add(new BookReader(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRents));
        readerList.add(new BookReader(2L, "Joe", "Doe", LocalDate.of(2018, 1, 1), bookRents));
        readerList.add(new BookReader(3L, "Maggy", "Smith", LocalDate.of(2018, 1, 1), bookRents));
        List<BookReaderDto> readerDtoList = new ArrayList<>();
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        readerDtoList.add(new BookReaderDto(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRentsDto));
        readerDtoList.add(new BookReaderDto(2L, "Joe", "Doe", LocalDate.of(2018, 1, 1), bookRentsDto));
        readerDtoList.add(new BookReaderDto(3L, "Maggy", "Smith", LocalDate.of(2018, 1, 1), bookRentsDto));

        when(readerDbService.getAllReaders()).thenReturn(readerList);
        when(readerMapper.mapToBookReaderDtoList(readerList)).thenReturn(readerDtoList);
        //When&Then
        mockMvc.perform(get("/v1/readers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[1].firstName",is("Joe")));
    }


    @Test
    public void shouldGetReader() throws Exception {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        BookReader bookReader = new BookReader(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRents);
        BookReaderDto bookReaderDto = new BookReaderDto(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRentsDto);

        when(readerDbService.getReaderById(anyLong())).thenReturn(bookReader);
        when(readerMapper.mapToBookReaderDto(any(BookReader.class))).thenReturn(bookReaderDto);
        //When & Then
        mockMvc.perform(get("/v1/readers/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    public void shouldDeleteReader() throws Exception {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        BookReader bookReader = new BookReader(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRents);

        when(readerDbService.getReaderById(1L)).thenReturn(bookReader);
        readerDbService.deleteReader(1L);
        //When&Then
        mockMvc.perform(get("/v1/readers/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateReader() throws Exception {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        BookReader bookReader = new BookReader(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRents);
        BookReaderDto bookReaderDto = new BookReaderDto(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRentsDto);

        when(readerDbService.saveReader(bookReader)).thenReturn(bookReader);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(bookReaderDto);
        //When&Then
        mockMvc.perform(post("/v1/readers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateReader() throws Exception {
        //Given
        List<BookRent> bookRents = new ArrayList<>();
        List<BookRentDto> bookRentsDto = new ArrayList<>();
        BookReader bookReader = new BookReader(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRents);
        BookReaderDto bookReaderDto = new BookReaderDto(1L, "John", "Helena", LocalDate.of(2018, 1, 1), bookRentsDto);

        when(readerDbService.saveReader(readerMapper.mapToBookReader(bookReaderDto))).thenReturn(bookReader);
        when(readerMapper.mapToBookReaderDto(bookReader)).thenReturn(bookReaderDto);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(bookReaderDto);
        //When&Then
        mockMvc.perform(put("/v1/readers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
