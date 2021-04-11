package com.bpjsk.monitor.requestobject;

import lombok.Data;

@Data
public class PaginationReqObj {
    protected Integer page =0;
    protected Integer size=10;
    protected String sort;
    protected String sortBy;
}
