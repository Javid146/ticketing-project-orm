package com.cydeo.repository;

import com.cydeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    //todo we will create all queries here
    //ready Spring queries with required name convention, derive query, @Query(JPA or Native)
}