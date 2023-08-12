package com.site.bemystory.domain;

import com.site.bemystory.dto.ImageDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "idx")
    private int index;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "img_url")
    private String imgUrl;

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
    public Image(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ImageDTO.Response toDTO(){
        return ImageDTO.Response.builder().index(this.index).imgUrl(this.imgUrl).build();
    }
}
