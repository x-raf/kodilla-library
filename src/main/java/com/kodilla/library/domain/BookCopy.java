package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "book_copies")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOOK_COPY_ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = BookTitle.class)
    @JoinColumn(name = "TITLE_ID")
    private BookTitle bookTitle;

    //@Enumerated(EnumType.STRING)
    @Column(length = 10)
    //private LibraryBookStatus libraryBookStatus;
    private String bookStatus;

    @OneToMany(targetEntity = BookRent.class,
    mappedBy = "bookCopy")
    private List<BookRent> bookRents = new ArrayList<>();
}
