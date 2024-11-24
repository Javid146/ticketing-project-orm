package com.cydeo.mapper;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProjectDTO convertToDto(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    public Project convertToEntity(ProjectDTO dto) {
        return modelMapper.map(dto, Project.class);
    }
}
