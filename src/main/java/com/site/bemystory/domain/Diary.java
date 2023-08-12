package com.site.bemystory.domain;


import com.site.bemystory.dto.DiaryDTO;
import com.site.bemystory.dto.DiaryRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Diary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diary_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "contents")
    private String contents;

    @Column(name = "genre")
    private String genre;

    @Column(name = "date")
    private String date;

    @OneToOne(mappedBy = "diary", fetch = FetchType.LAZY)
    private Book book;

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
    public Diary(String title, String contents, String genre, String date, User user) {
        this.title = title;
        this.contents = contents;
        this.genre = genre;
        this.date = date;
        this.user = user;
    }

    public DiaryDTO.AIRequest toDTO(){
        return DiaryDTO.AIRequest.builder()
                .title(this.title)
                .contents(this.contents)
                .genre(this.genre)
                .build();
    }

    public DiaryRequest toRequest(){
        return DiaryRequest.builder()
                .date(this.date)
                .title(this.title)
                .contents(this.contents)
                .genre(this.genre)
                .build();
    }
}
