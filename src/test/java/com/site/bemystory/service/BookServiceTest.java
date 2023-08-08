package com.site.bemystory.service;

import com.site.bemystory.domain.Diary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class BookServiceTest {
    @MockBean
    BookService bookService;
    @MockBean
    DiaryService diaryService;
    @MockBean
    UserService userService;

    @Test
    void uploadImage() {
        String url = "https://oaidalleapiprodscus.blob.core.windows.net/private/org-USXx31yZtxo5qNXm8pwxPAYN/user-ciD8mwlxH38f04irgcGhNvBA/img-gma8tr4wh9CfM9p76nFSGDpj.png?st=2023-08-07T08%3A43%3A33Z&se=2023-08-07T10%3A43%3A33Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-08-07T03%3A46%3A44Z&ske=2023-08-08T03%3A46%3A44Z&sks=b&skv=2021-08-06&sig=aYCm8Eskta4Qj0qzToKL5GNQQ0uLwNWZ6VRaqowA06Y%3D";
        System.out.println(bookService.uploadImage(url));
    }
}