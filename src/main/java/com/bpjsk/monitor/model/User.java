package com.bpjsk.monitor.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true,name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Integer isActive;

    @ManyToOne()
    @JoinColumn(name="role_id", referencedColumnName = "role_id")
    private UserRole role;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

}
