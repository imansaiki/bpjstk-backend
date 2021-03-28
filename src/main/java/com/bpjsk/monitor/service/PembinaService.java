package com.bpjsk.monitor.service;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.repository.PembinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PembinaService {
    @Autowired
    PembinaRepository pembinaRepository;

    public Page<Pembina> getAll(Integer start, Integer size, String sort, String sortBy, String nama, String kodePembina, String kota) {
        Pageable pageable = PageRequest.of(start,size, sort.isEmpty() || sort.equalsIgnoreCase("asc")? Sort.Direction.ASC : Sort.Direction.DESC, sortBy.isEmpty()?"id":sortBy);
        Page<Pembina> pembinaPage = pembinaRepository.findAll(pageable);
        return pembinaPage;
    }
}
