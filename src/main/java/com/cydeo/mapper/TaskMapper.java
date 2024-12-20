package com.cydeo.mapper;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component

public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //convertToEntity
    public Task convertToEntity(TaskDTO dto){
        return modelMapper.map(dto,Task.class);
    }

    //convertToDto
    public TaskDTO convertToDto(Task entity){return modelMapper.map(entity,TaskDTO.class);}
}
