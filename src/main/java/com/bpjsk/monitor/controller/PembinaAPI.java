package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.requestobject.PembinaReqObj;
import com.bpjsk.monitor.service.PembinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/pembina")
public class PembinaAPI {

    @Autowired
    PembinaService pembinaService;

    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAll (PembinaReqObj pembinaReqObj, HttpServletRequest request) {
        HashMap response = new HashMap<String,Object>();
        Page<Pembina> pembinaPage = null;
        pembinaPage =pembinaService.getAll(pembinaReqObj);
        response.put("message","Get Detail Success");
        response.put("data",pembinaPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
