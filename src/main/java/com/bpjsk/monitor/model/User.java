package com.bpjsk.monitor.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true,name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "level")
    private String level;

    @Column(name = "is_active")
    private Integer isActive;

    @ManyToOne()
    @JoinColumn(name="role_id", referencedColumnName = "role_id")
    private UserRole role;

}
