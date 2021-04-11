package com.bpjsk.monitor.specification;

import lombok.Data;

import java.util.List;

@Data
public class QueryBuilderCriteria {
    protected String condition;
    protected String field;
    protected String operator;
    protected String value;
    protected List<QueryBuilderCriteria> rules;

}
