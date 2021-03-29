package com.bpjsk.monitor.requestobject;

import lombok.Data;

@Data
public class PerusahaanReqObj extends PaginationReqObj{
    private String nppLk;
    private String nppEq;
    private String namaLk;
    private String kotaLk;
    private String kodePembinaEq;
}
