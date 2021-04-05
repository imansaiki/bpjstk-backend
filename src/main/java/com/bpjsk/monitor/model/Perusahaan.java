package com.bpjsk.monitor.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_perusahaan")
public class Perusahaan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(unique=true,name = "npp")
    private String npp;

    @Column(name = "nama")
    private String nama;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "kota")
    private String kota;

    @Column(name = "nama_pic")
    private String namaPic;

    @Column(name = "jabatan_pic")
    private String jabatanPic;

    @Column(name = "telepon")
    private String telepon;

    @Column(name = "email")
    private String email;

    @Column(name = "kode_pembina")
    private String kodePembina;

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
