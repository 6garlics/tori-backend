package com.site.bemystory.domain;

import jakarta.persistence.*;

@Entity
@Embeddable
public class Page {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pageId;
    private int number; //동화책 내에서 페이지 번호
    private String img_url;
    private String text;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
}
