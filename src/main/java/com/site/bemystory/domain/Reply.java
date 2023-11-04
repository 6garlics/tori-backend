package com.site.bemystory.domain;

import com.sun.istack.NotNull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
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

    @ManyToOne    //동화가 삭제되면 그에 달린 댓글도 삭제됨
    @JoinColumn(name = "book_id")
    @NotNull
    private Book book;

    //댓글이 속한 댓글 번호(대댓글에 필요함)
    @Column(name = "grp", nullable = false)
    private Long grp;

    //같은 grp 중에 순서 grps
    @Column(name = "grps", nullable = false)
    @NotNull
    private Long grps;

    //댓글의 깊이(모댓글 0, 대댓글 1)
    @Column(name = "grpl")
    @NotNull
    private Integer grpl;

    @ManyToOne(cascade = CascadeType.PERSIST)   //유저의 닉네임 변경 시 반영됨
    @JoinColumn(name = "user_id")
    @NotNull
    private User writer;

    @Column(name = "content")
    private String content;

    @Column(name = "date", nullable = false)
    private String date;

    @Builder
    public Reply(Long grp, Long grps, Integer grpl, User writer, Book book){
        this.grp=grp;
        this.grps=grps;
        this.grpl=grpl;
        this.writer=writer;
        this.book=book;
    }

}
