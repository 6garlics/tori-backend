package com.site.bemystory.domain;

import com.site.bemystory.dto.BookUpdate;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Entity
public class Text {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_id")
    private Long id;
    @Column(name = "idx")
    private int index;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "text")
    private String text;

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
    public Text(int index, String text, Book book) {
        this.index = index;
        this.text = text;
        this.book = book;
    }

    public void update(String text){
        this.text=text;
    }
    public void delete(){
        this.isDeleted=true;
        this.deletedAt=new Timestamp(System.currentTimeMillis());
    }
}
