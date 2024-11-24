package com.cydeo.converter;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDtoConverter implements Converter<String, UserDTO> {

    UserService userService;

    //todo @Lazy means, UserDtoConverter is only called when required
    public UserDtoConverter(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override //todo takes String converts into UserDTO
    public UserDTO convert(String source) {
        return userService.findByUserName(source);
    }

    /*why converter is used.
     * todo let's assume we have form dropdown with roles. we select admin (that is String), fill form and save
     * new user. When we save new user has to be in table of users in UI which is userDto object.
     * UserDto class has RoleDto object. So in order to convert admin String to roleDto object we use converter
     * */
}