package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDTO> {

    RoleService roleService;

    //injection
    //todo @Lazy means, RoleDtoConverter is only called when required
    public RoleDtoConverter(@Lazy RoleService roleService) {
        this.roleService = roleService;
    }

    @Override //todo takes String converts into RoleDto
    public RoleDTO convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }
        return roleService.findById(Long.parseLong(source));
    }

    /*why converter is used.
     * todo let's assume we have form dropdown with roles. we select admin (that is String), fill form and save
     * new user. When we save new user has to be in table of users in UI which is userDto object.
     * UserDto class has RoleDto object. So in order to convert admin String to roleDto object we use converter
     * */
}