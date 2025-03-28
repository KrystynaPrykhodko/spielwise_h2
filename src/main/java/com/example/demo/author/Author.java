package com.example.demo.author;
import com.example.demo.book.Book;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Author {
    @Id
    @SequenceGenerator(
            name = "author_sequence",
            sequenceName = "author_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_sequence"
    )

    private Long id;

    @Setter
    private String authorName;

    @Setter
    private LocalDate birthDate;

    @Setter
    private String nationality;
    
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Verhindert endlose Rekursion
    private List<Book> books;  // Ein Author hat mehrere Books
}
