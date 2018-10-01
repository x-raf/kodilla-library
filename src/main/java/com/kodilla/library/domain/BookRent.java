package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "book_rents")
public class BookRent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    private BookCopy bookCopy;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private BookReader bookReader;

    @Column
    @NotNull
    private LocalDate rentStartDate;

    @Column
    private LocalDate rentEndDate;
}
