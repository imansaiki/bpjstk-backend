package com.bpjsk.monitor.requestobject;

import lombok.Data;

@Data
public class PembinaReqObj extends PaginationReqObj{
    private String nipLk;
    private String nipEq;
    private String namaLk;
    private String kodePembinaEq;
    private String kodePembinaLk;
    private String kotaEq;
    private String kotaLk;
    private String teleponLk;
    private String teleponEq;
    private String emailEq;
    private String emailLk;
}
