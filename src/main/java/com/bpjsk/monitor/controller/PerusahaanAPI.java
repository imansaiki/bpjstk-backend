package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.exception.CustomException;
import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.Perusahaan;
import com.bpjsk.monitor.requestobject.PerusahaanReqObj;
import com.bpjsk.monitor.service.PembinaService;
import com.bpjsk.monitor.service.PerusahaanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(path="/perusahaan")
public class PerusahaanAPI {

    @Autowired
    PerusahaanService perusahaanService;

    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAll (PerusahaanReqObj perusahaanReqObj,
                                                       HttpServletRequest request) {
        HashMap response = new HashMap<String,Object>();
        Page<Perusahaan> perusahaanPage = null;
        if (request.isUserInRole("ROLE_ADMIN")){
            perusahaanPage = perusahaanService.getAll(perusahaanReqObj);
            //log.info(perusahaanReqObj.getPage()+" "+perusahaanReqObj.getSize()+" admin");
        }
        if (request.isUserInRole("ROLE_PEMBINA")){
            perusahaanPage = perusahaanService.getAll(perusahaanReqObj,request.getUserPrincipal().getName());
            //log.info(perusahaanReqObj.getPage()+" "+perusahaanReqObj.getSize()+" pembina");
        }

        response.put("data",perusahaanPage);
        response.put("message","Get Detail Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/save", method= RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> save (@RequestBody Perusahaan perusahaan,
                                                       HttpServletRequest request) {
        HashMap response = new HashMap<String,Object>();
        try {
            perusahaanService.save(perusahaan);
        } catch (Exception e) {
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("message","Submitted");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value="/saveAll", method= RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> saveAll (@RequestBody  List<Perusahaan> perusahaanList,
                                                        HttpServletRequest request) {
        HashMap response = new HashMap<String,Object>();
        try {
            perusahaanService.save(perusahaanList);
        } catch (Exception e) {
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("message","Submitted");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value="/delete", method= RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deletePerusahaan(@RequestBody Perusahaan perusahaan, HttpServletRequest request) {
        HashMap response = new HashMap<String,Object>();
        Perusahaan deleted = null;
        if (perusahaan.getId()!=null){
            try {
                deleted  = perusahaanService.deletePerusahaan(perusahaan.getId());
            }catch (CustomException e){
                response.put("message",e.getMessage());
                return new ResponseEntity<>(response, e.getHttpStatus());
            }catch (Exception e){
                response.put("message",e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        response.put("message","Delete perusahaan Success");
        response.put("data",deleted);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
