package com.site.bemystory.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Text {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_id")
    private Long id;
    @Column(name = "idx")
    private int index;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "text")
    private String text;

    @Builder
    public Text(int index, String text, Book book) {
        this.index = index;
        this.text = text;
        this.book = book;
    }
}
