package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.requestobject.PembinaReqObj;
import com.bpjsk.monitor.service.PembinaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/pembina")
public class PembinaAPI {

    @Autowired
    PembinaService pembinaService;

    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAll (PembinaReqObj pembinaReqObj, HttpServletRequest request) {
        HashMap response = new HashMap<String,Object>();
        Page<Pembina> pembinaPage = null;
        if (request.isUserInRole("ROLE_ADMIN")){
            pembinaPage = pembinaService.getAll(pembinaReqObj, null);
            log.info(pembinaReqObj.getPage()+" "+pembinaReqObj.getSize()+" admin");
        }
        if (request.isUserInRole("ROLE_PEMBINA")){
            pembinaPage = pembinaService.getAll(pembinaReqObj,request.getUserPrincipal().getName());
            log.info(pembinaReqObj.getPage()+" "+pembinaReqObj.getSize()+" pembina");
        }
        pembinaPage =pembinaService.getAll(pembinaReqObj, request.getUserPrincipal().getName());
        response.put("message","Get Detail Success");
        response.put("data",pembinaPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
