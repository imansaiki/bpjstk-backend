package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.Surat;
import com.bpjsk.monitor.requestobject.SuratReqObj;
import com.bpjsk.monitor.service.PerusahaanService;
import com.bpjsk.monitor.service.SuratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path="/surat")
public class SuratAPI {

    @Autowired
    SuratService suratService;

    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAll (SuratReqObj suratReqObj, Authentication authentication) {
        HashMap response = new HashMap<String,Object>();
        Page<Surat> suratPage =suratService.getAll(suratReqObj);
        response.put("message","Get Detail Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
