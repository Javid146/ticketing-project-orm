package com.cydeo.service;
import com.cydeo.dto.UserDTO;


import java.util.List;

public interface UserService{

    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO dto);
    UserDTO update(UserDTO dto);
    void deleteByUserName(String username);
    void delete(String username);
    List<UserDTO> listAllByRole(String role);

    /*why converter is used.
    * todo let's assume we have form dropdown with roles. we select admin (that is String), fill form and save
    * new user. When we save new user has to be in table of users in UI which is userDto object.
    * UserDto class has RoleDto object. So in order to convert admin String to roleDto object we use converter
    * */



}