package com.mini.project.courseservice.client;

import com.mini.project.courseservice.client.dto.UsersDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserClient userClient;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userClient, "userServiceUrl", "http://localhost:8080/users/");
    }

    @Test
    void testGetUserById() {
        UsersDto mockUser = new UsersDto();
        mockUser.setId(1L);
        mockUser.setName("John Doe");

        when(restTemplate.getForObject("http://localhost:8080/users/1", UsersDto.class))
                .thenReturn(mockUser);

        UsersDto user = userClient.getUserById(1L);

        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
    }
}