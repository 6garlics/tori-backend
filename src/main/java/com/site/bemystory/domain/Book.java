package com.site.bemystory.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.site.bemystory.dto.BookDTO;
import com.site.bemystory.dto.BookOneRequest;
import com.site.bemystory.dto.BookUpdate;
import com.site.bemystory.dto.Page;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @JsonIgnore
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Text> texts;

    @JsonIgnore
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY)
    private Cover cover;

    @JsonIgnore
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

    public BookDTO.BookMeta toDTO(String cover){
        return BookDTO.BookMeta.builder()
                .bookId(this.bookId)
                .title(this.title)
                .coverUrl(cover)
                .date(this.date)
                .build();
    }

    public BookOneRequest toRequest(List<Page> pages){
        return BookOneRequest.builder()
                .bookId(this.bookId)
                .userName(this.user.getUserName())
                .title(this.title)
                .date(this.date)
                .genre(this.genre)
                .coverUrl(this.cover.getCoverUrl())
                .pages(pages)
                .build();
    }

    public void update(BookUpdate update){
        this.title= update.getTitle();
    }
    public void delete(){
        this.isDeleted=true;
        this.deletedAt=new Timestamp(System.currentTimeMillis());
    }
}
