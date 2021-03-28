package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.model.Pembina;
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
    public ResponseEntity<Map<String, Object>> getAll (@RequestParam(name = "start") Integer start, @RequestParam(name = "size") Integer size,
                                                          @RequestParam(name = "sort") Optional<String> sort,@RequestParam(name = "sortBy") Optional<String> sortBy,
                                                          @RequestParam(name = "nama") Optional<String> nama,@RequestParam(name = "kodePembina") Optional<String> kodePembina,
                                                          @RequestParam(name = "kota") Optional<String> kota,
                                                          Authentication authentication) {
        HashMap response = new HashMap<String,Object>();
        Page<Pembina> pembinaPage =pembinaService.getAll(start,size,sort.orElse("asc"),sortBy.orElse("id"),nama.orElse(null),kodePembina.orElse(null),kota.orElse(null));
        response.put("message","Get Detail Success");
        response.put("data",pembinaPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
