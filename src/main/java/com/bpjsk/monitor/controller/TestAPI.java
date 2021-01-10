package com.bpjsk.monitor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/api")
public class TestAPI {
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseEntity<String> testAPI(){
        return new ResponseEntity<String>("HAHAHHAA", HttpStatus.OK);
    }
}
