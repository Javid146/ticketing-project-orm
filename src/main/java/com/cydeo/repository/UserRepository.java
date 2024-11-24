package com.cydeo.repository;

import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    @Transactional //is used for derived query. todo @Modifying is used for Native sql or @JPQL

    void deleteByUserName(String userName);

    List<User> findAllByRoleDescriptionIgnoreCase(String description);

}
