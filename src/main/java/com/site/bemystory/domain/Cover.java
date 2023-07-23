package com.site.bemystory.domain;

import jakarta.persistence.*;

@Entity
public class Cover {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_id")
    private Long id;
    @Column(name = "cover_url")
    private String coverUrl;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
