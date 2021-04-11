package com.bpjsk.monitor.requestobject;

import lombok.Data;

@Data
public class SuratReqObj extends PaginationReqObj{
    private String kodeSuratEq;
    private String kodeSuratLk;
    private String nppEq;
    private String nppLk;
    private String namaPengirimLk;
    private String namaPengirimEq;
    private String jenisSuratLk;
    private String jenisSuratEq;
    private String judulSuratEq;
    private String judulSuratLk;
    private String tanggalStart;
    private String tanggalEnd;
    private String namaPerusahaanLk;
}
