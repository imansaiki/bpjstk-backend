package com.bpjsk.monitor.service;

import com.bpjsk.monitor.model.Surat;
import com.bpjsk.monitor.repository.SuratRepository;
import com.bpjsk.monitor.requestobject.SuratReqObj;
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

    public Page<Surat> getAll(SuratReqObj suratReqObj) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (suratReqObj.getSort()!=null){
            if (suratReqObj.getSort().equalsIgnoreCase("DESC")){
                sort = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest.of(suratReqObj.getPage()!=null? suratReqObj.getPage() : 0,suratReqObj.getSize()!=null? suratReqObj.getSize() : 0);
        Page<Surat> suratPage = suratRepository.findAll(pageable);
        return  suratPage;
    }
}
