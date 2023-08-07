package com.site.bemystory.domain;


import com.site.bemystory.dto.DiaryDTO;
import jakarta.persistence.*;
import lombok.*;


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
}
