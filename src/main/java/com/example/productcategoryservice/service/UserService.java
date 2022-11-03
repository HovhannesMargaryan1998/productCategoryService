package com.example.productcategoryservice.service;

import com.example.productcategoryservice.dto.CreateUserDto;
import com.example.productcategoryservice.dto.UserAuthDto;
import com.example.productcategoryservice.dto.UserAuthResponseDto;
import com.example.productcategoryservice.mapper.UserMapper;
import com.example.productcategoryservice.model.Role;
import com.example.productcategoryservice.model.User;
import com.example.productcategoryservice.repository.UserRepository;
import com.example.productcategoryservice.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return user;
    }

    public UserAuthResponseDto auth(UserAuthDto userAuthDto) {
        Optional<User> byEmail = userRepository.findByEmail(userAuthDto.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (passwordEncoder.matches(userAuthDto.getPassword(), user.getPassword())) {
                return UserAuthResponseDto.builder()
                        .token(jwtTokenUtil.generateToken(user.getEmail()))
                        .userDto(userMapper.map(user))
                        .build();
            }
        }
        return null;

    }
}