package com.bpjsk.monitor.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_surat")
public class Surat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "kode_surat")
    private String kodeSurat;

    @Column(name = "judul_surat")
    private String judulSurat;

    @Column(name = "tanggal_surat")
    private Date tanggalSurat;

    @Column(name = "jenis_surat")
    private String jenisSurat;

    @Column(name = "npp")
    private String npp;

    @Column(name = "nama_perusahaan")
    private String namaPerusahaan;

    @Column(name = "nama_pengirim")
    private String namaPengirim;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "is_deleted")
    private Integer isDeleted=0;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
