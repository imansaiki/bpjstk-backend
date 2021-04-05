package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.requestobject.PaginationReqObj;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/api")
public class TestAPI {
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> testAPI(PaginationReqObj paginationReqObj){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("Data",paginationReqObj);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
