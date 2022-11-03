package com.example.productcategoryservice.mapper;



import com.example.productcategoryservice.dto.CreateUserDto;
import com.example.productcategoryservice.dto.UserDto;
import com.example.productcategoryservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User map(CreateUserDto createUserDto);
    UserDto map(User user);



}
