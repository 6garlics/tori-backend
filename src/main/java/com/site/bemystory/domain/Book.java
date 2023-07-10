package com.site.bemystory.domain;

import java.util.List;
public class Book {
    private StoryBook storyBook;
    private List<Page> pages;

    public StoryBook getStoryBook() {
        return storyBook;
    }

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
