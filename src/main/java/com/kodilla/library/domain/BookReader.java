package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "readers")
public class BookReader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "READER_ID", updatable = false, nullable = false)
    private Long id;

    @Column (name = "FIRST_NAME")
    @NotNull
    private String firstName;

    @Column (name = "LAST_NAME")
    @NotNull
    private String lastName;

    @Column (name = "ACCOUNT_CREATED_DATE")
    @NotNull
    private LocalDate accountCreatedDate;

    @OneToMany (targetEntity = BookRent.class,
    mappedBy = "bookReader")
    private List<BookRent> readerRents = new ArrayList<>();
}
