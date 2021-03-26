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

    @Column(name = "nama_pengirim")
    private String namaPengirim;
}
