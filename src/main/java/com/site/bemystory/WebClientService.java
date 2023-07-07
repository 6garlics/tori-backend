package com.site.bemystory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WebClientService {
    @Autowired
    ResourceLoader resourceLoader;

    public InputStream post() throws  IOException{
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("subject", "장마");
        bodyMap.put("date", "2023.06.25");
        bodyMap.put("contents", "오늘 장마가 시작됐다. 후덥지근하고 짜증난다.");

        //webClient 기본설정
        WebClient webClient = WebClient.builder().baseUrl("https://yc2bgtwjbosyjziwdos7kbbvma0cdaql.lambda-url.eu-north-1.on.aws/").build();

        //api 요청
        Map<String, Object> response=webClient.post()
                .uri("/storybook")
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<String> paraphs = (List<String>) response.get("paragraphs");
        List<String> img_urls = (List<String>) response.get("image_urls");

        String imgUrl = img_urls.get(0);

        InputStream inputStream = new URL(imgUrl).openStream();

        //결과 확인
        log.info(response.toString());
        log.info(paraphs.toString());

        return inputStream;


    }
}
