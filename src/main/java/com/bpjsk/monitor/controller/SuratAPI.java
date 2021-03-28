package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.Surat;
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
    public ResponseEntity<Map<String, Object>> getAll (@RequestParam(name = "start") Integer start, @RequestParam(name = "size") Integer size,
                                                       @RequestParam(name = "sort") Optional<String> sort, @RequestParam(name = "sortBy") Optional<String> sortBy,
                                                       @RequestParam(name = "kodeSurat") Optional<String> kodeSurat, @RequestParam(name = "judulSurat") Optional<String> judulSurat,
                                                       @RequestParam(name = "namaPerusahaan") Optional<String> namaPerusahaan,@RequestParam(name = "npp") Optional<String> npp,
                                                       @RequestParam(name = "namaPengirim") Optional<String> namaPengirim,@RequestParam(name = "tanggalStart") Optional<String> tanggalStart,
                                                       @RequestParam(name = "tanggalEnd") Optional<String> tanggalEnd,
                                                       Authentication authentication) {
        HashMap response = new HashMap<String,Object>();
        Page<Surat> suratPage =suratService.getAll(start,size,sort.orElse("asc"),sortBy.orElse("id"),kodeSurat.orElse(null),
                judulSurat.orElse(null),namaPerusahaan.orElse(null),npp.orElse(null),
                namaPengirim.orElse(null),tanggalStart.orElse(null),tanggalEnd.orElse(null));
        response.put("message","Get Detail Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
