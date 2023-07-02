package com.site.bemystory.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StoryBook {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String subject;

    @OneToMany(mappedBy = "storyBook", fetch = FetchType.LAZY)
    private List<Page> pages = new ArrayList<>();
    /*private List<String> paragraphs;
    private List<String> image_urls;*/

    private String story_type;

    private LocalDate date;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStory_type() {
        return story_type;
    }

    public void setStory_type(String story_type) {
        this.story_type = story_type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public void addPage(Page page){
        this.pages.add(page);
    }
}
