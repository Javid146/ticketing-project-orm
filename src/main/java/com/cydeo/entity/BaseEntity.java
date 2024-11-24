package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //because we want postgres to generate id
    private Long id;
    //data below is created automatically to track records
    @Column(nullable = false, updatable = false) //cannot be null or updatable when data is created
    private LocalDateTime insertDateTime;
    @Column(nullable = false, updatable = false)
    private Long insertUserId; //who inserted data
    @Column(nullable = false) //cannot be null or updatable when data is created
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false, updatable = false)
    private Long lastUpdateUserId; //who updated data last

    //this is used if data is deleted from db, it will not be seen in UI
    //db will have data, but it will have is_deleted column, and if its value is true, it will not be fetched to UI
    private Boolean isDeleted=false;

    /*1. private Boolean isDeleted=false;
    2. User Class: @Where(clause = "is_deleted=false") todo if user is not deleted fetch it
    3. UserService: void delete(String username);      service method
    4. UerServiceImpl delete method: user.setIsDeleted(true); todo this method simply, makes is_deleted column true and saves it
    5. UserController delete method: userService.delete(username); //todo delete user from UI, but keep it in db for future reference
     */

    //todo when we create sth on db method below will trigger showing who created data
    //1L here is hard code, usually there is the id of user that used the db latest
    @PrePersist
    public void prePersist() {
        insertDateTime = LocalDateTime.now();
        insertUserId = 1L;
        lastUpdateDateTime = LocalDateTime.now();
        lastUpdateUserId = 1L;
    }

    //todo when we update sth on db method below will trigger showing who updated data. 1L rule is above is true here too
    @PreUpdate
    public void preUpdate() {
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = this.insertUserId;
    }
}