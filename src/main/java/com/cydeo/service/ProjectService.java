package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;

import java.util.List;

public interface ProjectService {

    ProjectDTO getByProjectCode(String projectCode);
    List<ProjectDTO> listAllProjects();
    void save(ProjectDTO dto);
    void update(ProjectDTO dto);
    void delete(String projectCode);
    void complete(String projectCode);
    List<ProjectDTO> listAllProjectDetails();

    List<ProjectDTO> readAllByAssignedManager(User assignedManager);
}
