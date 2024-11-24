package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service //todo means this class is bean that implements RoleService which will be injected in Controller
//to be bean a class should implement its interface and have @Service annot.
public class RoleServiceImpl implements RoleService {

    //todo RoleRepository interface injection to bring roles from db
    //todo we inject roleMapper class (Bean) because we need to convert entity to dto and vice versa
    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }

    //todo controller calls this method via UserService interface injection for all roles to be listed
    //todo this m. takes role list from db and converts entity to dto
    @Override
    public List<RoleDTO> listAllRoles() {
        //todo findAll is from JPA repository
//        return roleRepository.findAll().stream().map(roleMapper::convertToDto).collect(Collectors.toList());
        return roleRepository.findAll().stream().map(role -> mapperUtil.convertDtoToEntityAndViceVersa(role, new RoleDTO()))
                .collect(Collectors.toList());
    }

    //converts role String (Admin) from role dropdown on UI, to RoleDTO
    //for that gets selected role's id and finds it from DB and convert entity to dto.
    @Override
    public RoleDTO findById(Long id) {
//        return roleMapper.convertToDto(roleRepository.findById(id).get());
        return mapperUtil.convertDtoToEntityAndViceVersa(roleRepository.findById(id).get(), new RoleDTO());
    }
}