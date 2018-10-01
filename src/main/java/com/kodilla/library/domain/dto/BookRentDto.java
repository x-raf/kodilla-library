package com.kodilla.library.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookRentDto {

    private Long id;
    private Long bookCopyId;
    private Long bookReaderId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentEndDate;
}
