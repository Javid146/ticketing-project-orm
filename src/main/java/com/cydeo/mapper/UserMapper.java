package com.cydeo.mapper;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //convertToEntity
    public User convertToEntity(UserDTO dto){
        return modelMapper.map(dto,User.class);
    }

    //convertToDto
    public UserDTO convertToDto(User entity){return modelMapper.map(entity,UserDTO.class);}



    /*
todo Mapper class is used to convert dto to entity (db) and vice versa.
    when you connect UI or API with db you need to use it. Cause db data comes as Entity but API json data or data from UI is DTO.
    therefore we have those 2 methods above.
    1st we add model mapper dependency
    2nd we add bean at TicketingProjectOrmApplication class
    3rd inject that ModelMapper class here and use in constructor*/
}
