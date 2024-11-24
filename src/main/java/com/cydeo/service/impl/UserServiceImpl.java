package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service //todo means this class is bean that implements UserService which will be injected in Controller
//to be bean a class should implement its interface and have @Service annot.
public class UserServiceImpl implements UserService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;
                                                                                                                //use ProjectService only if needed
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TaskRepository taskRepository, @Lazy ProjectService projectService, TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    //todo controller calls this method via UserService interface injection for all users to be listed
    //this m. takes user list from db
    @Override
    public List<UserDTO> listAllUsers() {
       return userRepository.findAll().stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    //get user from db and convert to DTO to use in userController (UI)
    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUserName(username);
        return userMapper.convertToDto(user);
    }

    @Override
    public void save(UserDTO dto) {
        userRepository.save(userMapper.convertToEntity(dto));
    }

    @Override
    public UserDTO update(UserDTO dto) {
        //find unupdated user from db
        User user = userRepository.findByUserName(dto.getUserName());

        //convert updated user dto to entity object
        User convertedUser = userMapper.convertToEntity(dto);
        System.out.println("convertedUser 1 = " + convertedUser.getId());

        //setId of updated user to existing userId, so there is duplicate user created. same user updated
        convertedUser.setId(user.getId());
        System.out.println("user.getId() = " + user.getId());
        System.out.println("convertedUser 2 = " + convertedUser.getId());
        //save updated user
        userRepository.save(convertedUser);
        //find user entity convert to user dto to be used in UI table
        System.out.println("findByUserName(dto.getUserName()) = " + findByUserName(dto.getUserName()));
        return findByUserName(dto.getUserName());
    }

    //delete from db
    @Override
    public void deleteByUserName(String username) {
         userRepository.deleteByUserName(username);
    }

    //change flag and keep in db. this method is used to delete user from db, but keep it there with is_deleted=true column
    @Override
    public void delete(String username) {
        User user = userRepository.findByUserName(username);

        if (checkIfUserCanBeDeleted(user)) {
            //set is_deleted column true
            user.setIsDeleted(true);
            //db table shows for ex: javidM - 5. so if you want to recreate same username you can. because current will
            //be like "javidM - 5"
            user.setUserName(user.getUserName() + "-" + user.getId());
            userRepository.save(user);
        }
    }

    //if user has open tasks, or projects under his name
    private boolean checkIfUserCanBeDeleted(User user) {

        switch (user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.readAllByAssignedManager(user);
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.readAllByAssignedEmployee(user);
                return taskDTOList.size() == 0;
            default: return true;
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String roleDescription) {

        List <User> users = userRepository.findAllByRoleDescriptionIgnoreCase(roleDescription);
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }
}