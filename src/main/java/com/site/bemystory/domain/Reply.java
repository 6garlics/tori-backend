package com.site.bemystory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)    //동화가 삭제되면 그에 달린 댓글도 삭제됨
    @JoinColumn(name = "book_id")
    private Book book;

    //댓글이 속한 댓글 번호(대댓글에 필요함)
    @Column(name = "grp")
    private Long grp;

    //같은 grp 중에 순서 grps
    @Column(name = "grps")
    private Long grps;

    //댓글의 깊이(모댓글 0, 대댓글 1)
    @Column(name = "grpl")
    private Long grpl;

    @ManyToOne(cascade = CascadeType.PERSIST)   //유저의 닉네임 변경 시 반영됨
    @JoinColumn(name = "user_id")
    private User writer;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private String date;

}
