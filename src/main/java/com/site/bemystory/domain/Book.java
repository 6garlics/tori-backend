package com.site.bemystory.domain;

import com.site.bemystory.dto.BookDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "date")
    private String date;

    @OneToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Text> texts;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY)
    private Cover cover;

    @Builder
    public Book(String title) {
        this.title = title;
    }

    public BookDTO.BookShelf toDTO(){
        return BookDTO.BookShelf.builder()
                .bookId(getBookId())
                .title(getTitle())
                .build();
    }

}
