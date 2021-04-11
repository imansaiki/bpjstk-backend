package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.Perusahaan;
import com.bpjsk.monitor.model.Surat;
import com.bpjsk.monitor.requestobject.SuratReqObj;
import com.bpjsk.monitor.service.PerusahaanService;
import com.bpjsk.monitor.service.SuratService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(path="/surat")
public class SuratAPI {

    @Autowired
    SuratService suratService;

    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAll (SuratReqObj suratReqObj, HttpServletRequest request) {
        HashMap response = new HashMap<String,Object>();
        Page<Surat> suratPage = null;
        if (request.isUserInRole("ROLE_ADMIN")){
            suratPage = suratService.getAll(suratReqObj, null);
            log.info(suratReqObj.getPage()+" "+suratReqObj.getSize()+" admin");
        }
        if(request.isUserInRole("ROLE_PEMBINA")){
            suratPage = suratService.getAll(suratReqObj,request.getUserPrincipal().getName());
            log.info(suratReqObj.getPage()+" "+suratReqObj.getSize()+" pembina");
        }
        response.put("data",suratPage);
        response.put("message","Get Detail Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value="/save", method= RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> save (@RequestBody Surat surat,
                                                     HttpServletRequest request) {
        HashMap response = new HashMap<String,Object>();
        try {
            suratService.save(surat);
        } catch (Exception e) {
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("message","Submitted");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @RequestMapping(value="/saveAll", method= RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> save (@RequestBody List<Surat> surat,
                                                     HttpServletRequest request) {
        HashMap response = new HashMap<String,Object>();
        try {
            suratService.save(surat);
        } catch (Exception e) {
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("message","Submitted");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
