package com.bpjsk.monitor.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tb_pembina")
public class Pembina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(unique=true,name = "nik")
    private String nik;

    @Column(name = "kode_pembina")
    private String kodePembina;

    @Column(name = "nama")
    private String nama;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "kota")
    private String kota;

    @Column(name = "telepon")
    private String telepon;

    @Column(name = "email")
    private String email;

}
