package com.bpjsk.monitor.service;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.repository.PembinaRepository;
import com.bpjsk.monitor.requestobject.PembinaReqObj;
import com.bpjsk.monitor.specification.PembinaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PembinaService {
    @Autowired
    PembinaRepository pembinaRepository;

    public Page<Pembina> getAll(PembinaReqObj pembinaReqObj, String nikUser) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (pembinaReqObj.getSort()!=null){
            if (pembinaReqObj.getSort().equalsIgnoreCase("DESC")){
                sort = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest.of(pembinaReqObj.getPage(),pembinaReqObj.getSize(),Sort.Direction.ASC, "id");

        Specification<Pembina> specification = Specification.where(new PembinaSpecification("isDeleted","=","0"));
        if(pembinaReqObj.getKotaLk()!=null){
            specification= specification.and(new PembinaSpecification("kota","like", pembinaReqObj.getKotaLk()));
        }
        if(pembinaReqObj.getNikLk()!=null){
            specification= specification.and(new PembinaSpecification("nik","like", pembinaReqObj.getNikLk()));
        }
        if(pembinaReqObj.getKodePembinaLk()!=null){
            specification= specification.and(new PembinaSpecification("kodePembina","like", pembinaReqObj.getKodePembinaLk()));
        }
        if(pembinaReqObj.getEmailLk()!=null){
            specification= specification.and(new PembinaSpecification("email","like", pembinaReqObj.getEmailLk()));
        }
        if(pembinaReqObj.getTeleponLk()!=null){
            specification= specification.and(new PembinaSpecification("telepon","like", pembinaReqObj.getTeleponLk()));
        }
        return pembinaRepository.findAll(specification,pageable);
    }
}
