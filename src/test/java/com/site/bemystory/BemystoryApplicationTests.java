package com.site.bemystory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class BemystoryApplicationTests {

    @Autowired
    private WebClientService webClientService;
    @Autowired
    private FileUploadController fileUploadController;
    @Test
    void post() throws IOException {
        webClientService.post();
        fileUploadController.uploadFile();
    }

}
