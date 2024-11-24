package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tasks")
@Where(clause = "is_deleted=false") //every user will have if userIsNotDeleted (false) fetch it from db.
//whatever repo interacts uses User entity, this query @Where(clause = "is_deleted=false") will be automatically added to each query
public class Task extends BaseEntity {

    private String taskSubject;
    private String taskDetail;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    }
