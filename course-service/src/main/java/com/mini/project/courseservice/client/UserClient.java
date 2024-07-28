package com.mini.project.courseservice.client;

import com.mini.project.courseservice.client.dto.UsersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    @Value("${sevice.user.url.base}")
    private String userServiceUrl;


    public UsersDto getUserById(Long id) {
        return restTemplate.getForObject(userServiceUrl + id, UsersDto.class);
    }
}
