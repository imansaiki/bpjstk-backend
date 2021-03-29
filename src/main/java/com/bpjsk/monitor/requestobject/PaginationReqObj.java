package com.bpjsk.monitor.requestobject;

import lombok.Data;

@Data
public class PaginationReqObj {
    protected Integer page;
    protected Integer size;
    protected String sort;
    protected String sortBy;
}
