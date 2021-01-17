package com.bpjsk.monitor.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_employee")
public class TbEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(unique=true,name = "nik")
    private String nik;

    @Column(name = "name")
    private String name;
}
