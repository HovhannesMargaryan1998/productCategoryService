package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.CreateUserDto;
import com.example.productcategoryservice.dto.UserAuthDto;
import com.example.productcategoryservice.dto.UserAuthResponseDto;
import com.example.productcategoryservice.mapper.UserMapper;
import com.example.productcategoryservice.model.User;
import com.example.productcategoryservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("/users")
    public ResponseEntity<?> register(@RequestBody CreateUserDto createUserDto) {
        Optional<User> byEmail = userService.findByEmail(createUserDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user=userService.register(userMapper.map(createUserDto));
        return ResponseEntity.ok(userMapper.map(user));
    }

    @PostMapping("/users/auth")
    public ResponseEntity<?> auth(@RequestBody UserAuthDto userAuthDto) {
        UserAuthResponseDto auth = userService.auth(userAuthDto);
        if (auth !=null){
            return ResponseEntity.ok(auth);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
