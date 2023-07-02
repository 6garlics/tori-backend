package com.site.bemystory.domain;

import jakarta.persistence.*;

@Entity
@Embeddable
public class Page {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pageId;
    private int index; //동화책 내에서 페이지 번호
    private String img_url;
    private String text;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private StoryBook storyBook;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public StoryBook getStoryBook() {
        return storyBook;
    }

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }
}
