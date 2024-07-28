package com.mini.project.userservice.service;

import com.mini.project.userservice.dto.RegisterDto;
import com.mini.project.userservice.dto.UsersDto;
import com.mini.project.userservice.dto.mapper.UserMapper;
import com.mini.project.userservice.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public void register(RegisterDto registerDto) {
        var user = userMapper.toRegisterEntity(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }


    @Cacheable(value = "users", key = "#id")
    public UsersDto getUserById(Long id) {
        var user = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return userMapper.toDto(user);
    }

    public Page<UsersDto> getUsers(Pageable pageable) {
        return usersRepository.findAll(pageable)
                .map(userMapper::toDto);
    }
}
