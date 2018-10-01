package com.kodilla.library.domain.dto;

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
    private LocalDate rentStartDate;
    private LocalDate rentEndDate;
}
