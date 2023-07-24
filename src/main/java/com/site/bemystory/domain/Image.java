package com.site.bemystory.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "idx")
    private int index;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "img_url")
    private String imgUrl;

    @Builder
    public Image(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
