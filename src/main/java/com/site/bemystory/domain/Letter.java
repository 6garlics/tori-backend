package com.site.bemystory.domain;

import com.site.bemystory.dto.LetterRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Letter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long letterId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser;
    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUser;
    @Column(name = "content")
    private String content;
    @CreationTimestamp
    private LocalDateTime date;

    public Letter(User fromUser, User toUser, String content, Book book){
        this.fromUser=fromUser;
        this.toUser=toUser;
        this.book=book;
        this.content= content;
    }
}
