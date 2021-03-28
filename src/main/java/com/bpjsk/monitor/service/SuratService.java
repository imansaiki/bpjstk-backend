package com.bpjsk.monitor.service;

import com.bpjsk.monitor.model.Surat;
import com.bpjsk.monitor.repository.SuratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SuratService {

    @Autowired
    SuratRepository suratRepository;
    public Page<Surat> getAll(Integer start, Integer size, String sort, String sortBy, String kodeSurat, String judulSurat, String namaPerusahaan, String npp, String namaPengirim, String tanggalStart, String tanggalEnd) {
        Pageable pageable = PageRequest.of(start,size, sort.isEmpty() || sort.equalsIgnoreCase("asc")? Sort.Direction.ASC : Sort.Direction.DESC, sortBy.isEmpty()?"id":sortBy);
        Page<Surat> suratPage = suratRepository.findAll(pageable);
        return  suratPage;
    }
}
