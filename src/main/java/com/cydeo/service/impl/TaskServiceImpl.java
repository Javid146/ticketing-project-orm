package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    TaskMapper taskMapper;
    TaskRepository taskRepository;
    ProjectMapper projectMapper;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository, ProjectMapper projectMapper, UserRepository userRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO findById(Long id) {
        //it stops you getting null exception
        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()) {
            return taskMapper.convertToDto(taskRepository.getById(id));
        }
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void saveTask(TaskDTO taskDTO) {

        taskDTO.setTaskStatus(Status.OPEN);
        taskDTO.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(taskDTO);

        taskRepository.save(task);
    }

    @Override
    public void updateTask(TaskDTO taskDTO) {

        //todo Optional protect you getting null pointer excepton
        Optional<Task> task = taskRepository.findById(taskDTO.getId());
        //updated task gets the id of its old version. so we don't see 2 same tasks (updated and old) with same id
        Task updatedTask = taskMapper.convertToEntity(taskDTO);

        if (task.isPresent()) {
            updatedTask.setId(task.get().getId());
            updatedTask.setTaskStatus(taskDTO.getTaskStatus() == null ? task.get().getTaskStatus() : taskDTO.getTaskStatus());
            updatedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(updatedTask);
        }
    }

    @Override
    public void deleteTask(Long id) {
        //todo Optional protect you getting null pointer excepton
        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()) {
            task.get().setIsDeleted(true);
            taskRepository.save(task.get());
        }
    }

    @Override
    public int totalInCompleteTasks(String projectCode) {
        return taskRepository.totalIncompleteTasks(projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO project) {
        List<TaskDTO> list = listAllByProject(project);
        list.forEach(taskDto -> deleteTask(taskDto.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO project) {
        List<TaskDTO> list = listAllByProject(project);
        list.forEach(taskDto -> {
            taskDto.setTaskStatus(Status.COMPLETE);
            updateTask(taskDto);
        });
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        User loggedInUser = userRepository.findByUserName("john@employee.com");
        List<Task> list = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status, loggedInUser);
        return list.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        User loggedInUser = userRepository.findByUserName("john@employee.com");
        List<Task> list = taskRepository.findAllByTaskStatusAndAssignedEmployee(status, loggedInUser);
        return list.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> readAllByAssignedEmployee(User assignedEmployee) {
        List<Task> list = taskRepository.findAllByAssignedEmployee(assignedEmployee);
        return list.stream()
                .map(taskMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO dto) {
        Optional<Task> task = taskRepository.findById(dto.getId());

        if (task.isPresent()) {
            task.get().setTaskStatus(dto.getTaskStatus());
            taskRepository.save(task.get());
        }
    }

    private List<TaskDTO> listAllByProject(ProjectDTO project) {

        List<Task> taskList = taskRepository.findAllByProject(projectMapper.convertToEntity(project));
        return taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }
}
