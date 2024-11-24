package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final TaskService taskService;
    ProjectMapper projectMapper;
    ProjectRepository projectRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public ProjectServiceImpl(ProjectMapper projectMapper, ProjectRepository projectRepository, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override //get project based on its code from db and convert to dto
    public ProjectDTO getByProjectCode(String projectCode) {
        return projectMapper.convertToDto(projectRepository.findByProjectCode(projectCode));
    }

    @Override //get all projects from db and convert to dto
    public List<ProjectDTO> listAllProjects() {
        return projectRepository.findAll().stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {

        //form on UI has no status, but table on UI has it. So when we create a project
        //we set it to OPEN before it is transferred to project db
        dto.setProjectStatus(Status.OPEN);
        System.out.println("dto = " + dto);
        Project project = projectRepository.save(projectMapper.convertToEntity(dto));

        project.setProjectStatus(Status.OPEN);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {
        //find unupdated project from db
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        //convert updated project dto to entity object
        Project convertedProject = projectMapper.convertToEntity(dto);
        //setId of updated project to existing userId, so there is duplicate project created. same project updated
        convertedProject.setId(project.getId());
        //update form has no status, so we get same project's status from db and assign to updated version of it
        convertedProject.setProjectStatus(project.getProjectStatus());
        //save updated project
        projectRepository.save(convertedProject);
    }

    @Override
    public void delete(String code) {
        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);

        project.setProjectCode(project.getProjectCode() +"-"+ project.getId());
        projectRepository.save(project);

        taskService.deleteByProject(projectMapper.convertToDto(project));
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        taskService.completeByProject(projectMapper.convertToDto(project));
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        UserDTO userDto = userService.findByUserName("harold@manager.com");
        User user = userMapper.convertToEntity(userDto);
        List<Project> projects = projectRepository.findAllByAssignedManager(user);

        return projects.stream().map(project -> {

            ProjectDTO obj = projectMapper.convertToDto(project);
            obj.setUnfinishedTaskCounts(taskService.totalInCompleteTasks(project.getProjectCode()));
            obj.setCompleteTaskCounts(taskService.totalCompletedTasks(project.getProjectCode()));
            return obj;

        }).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> readAllByAssignedManager(User assignedManager) {

        List<Project> list = projectRepository.findAllByAssignedManager(assignedManager);
        return list.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }
}
