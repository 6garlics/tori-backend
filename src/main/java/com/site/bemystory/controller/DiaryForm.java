package com.site.bemystory.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
public class DiaryForm {
    private String subject;

    private String contents;

    private String story_type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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
}
