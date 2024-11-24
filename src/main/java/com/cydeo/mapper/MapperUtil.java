package com.cydeo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {

    ModelMapper modelMapper = new ModelMapper();

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //access modifier, initializer of return type, return type, method name
    public <T> T convertDtoToEntityAndViceVersa(Object objectToBeConverted, T convertedObject) {
        return modelMapper.map(objectToBeConverted, (Type) convertedObject.getClass());
    }
}
