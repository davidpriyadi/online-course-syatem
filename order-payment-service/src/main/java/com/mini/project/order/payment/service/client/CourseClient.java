package com.mini.project.order.payment.service.client;


import com.mini.project.order.payment.service.client.dto.UserCourseDto;
import com.mini.project.order.payment.service.client.dto.UserCourseRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseClient {

    private final RestTemplate restTemplate;

    @Value("${sevice.course.url.base}")
    private String courseServiceUrl;

    public void createPremium(UserCourseRequestDto requestDto) {
        HttpEntity<UserCourseRequestDto> request = new HttpEntity<>(requestDto);
        restTemplate.exchange(courseServiceUrl, HttpMethod.POST, request, UserCourseDto.class);
    }
}
