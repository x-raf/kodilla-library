package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "book_titles")
public class BookTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TITLE_ID", updatable = false, nullable = false)
    private Long id;

    @Column (name = "TITLE")
    @NotNull
    private String title;

    @Column (name = "AUTHOR")
    @NotNull
    private String author;

    @Column (name = "PUBLICATION_DATE")
    @NotNull
    private int publicationDate;

    @OneToMany (targetEntity = BookCopy.class,
    mappedBy = "bookTitle")
    private List<BookCopy> bookCopies = new ArrayList<>();
}
