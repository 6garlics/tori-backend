package com.site.bemystory.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Cover {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_id")
    private Long id;
    @Column(name = "cover_url")
    private String coverUrl;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public Cover(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
