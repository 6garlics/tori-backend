package com.site.bemystory.domain;

import com.site.bemystory.dto.BookDTO;
import com.site.bemystory.dto.BookOneRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Builder
    public Book(String title) {
        this.title = title;
    }

    public BookDTO.BookShelf toDTO(String cover){
        return BookDTO.BookShelf.builder()
                .bookId(this.bookId)
                .title(this.title)
                .coverUrl(cover)
                .date(this.date)
                .build();
    }

    public BookOneRequest toRequest(List<String> texts, List<String> images){
        return BookOneRequest.builder()
                .bookId(this.bookId)
                .userName(this.user.getUserName())
                .title(this.title)
                .date(this.date)
                .genre(this.genre)
                .coverUrl(this.cover.getCoverUrl())
                .texts(texts)
                .images(images)
                .build();
    }

}
