package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import java.util.List;

public interface TaskService {

    TaskDTO findById(Long id);
    List<TaskDTO> listAllTasks();
    void saveTask(TaskDTO taskDTO);
    void updateTask(TaskDTO taskDTO);
    //task db has no anything unique so we use table's id for interactions
    void deleteTask(Long id);

    int totalInCompleteTasks(String projectCode);
    int totalCompletedTasks(String projectCode);

    void deleteByProject(ProjectDTO project);

    void completeByProject(ProjectDTO project);

    void updateStatus(TaskDTO task);

    List<TaskDTO> listAllTasksByStatusIsNot(Status status);

    List<TaskDTO> listAllTasksByStatus(Status status);

    List<TaskDTO> readAllByAssignedEmployee(User assignedEmployee);

}
