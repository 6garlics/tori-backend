package com.site.bemystory.domain;

import com.site.bemystory.dto.BookUpdate;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

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
    public Cover(String coverUrl, Book book) {
        this.coverUrl = coverUrl;
        this.book = book;
    }

    public void update(BookUpdate update){
        this.coverUrl= update.getCoverUrl();
    }

    public void delete(){
        this.isDeleted=true;
        this.deletedAt=new Timestamp(System.currentTimeMillis());
    }
}
