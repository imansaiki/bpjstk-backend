package com.bpjsk.monitor.service;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.Perusahaan;
import com.bpjsk.monitor.model.Surat;
import com.bpjsk.monitor.repository.PembinaRepository;
import com.bpjsk.monitor.repository.PerusahaanRepository;
import com.bpjsk.monitor.repository.SuratRepository;
import com.bpjsk.monitor.requestobject.SuratReqObj;
import com.bpjsk.monitor.specification.PerusahaanSpecification;
import com.bpjsk.monitor.specification.SuratSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuratService {

    @Autowired
    SuratRepository suratRepository;

    @Autowired
    PembinaRepository pembinaRepository;

    @Autowired
    PerusahaanRepository perusahaanRepository;

    public Page<Surat> getAll(SuratReqObj suratReqObj, String nikUser) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (suratReqObj.getSort()!=null){
            if (suratReqObj.getSort().equalsIgnoreCase("DESC")){
                sort = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest.of(suratReqObj.getPage()!=null? suratReqObj.getPage() : 0,suratReqObj.getSize()!=null? suratReqObj.getSize() : 0,sort,suratReqObj.getSortBy());
        Pembina pembina = null;
        if(nikUser!=null){
            pembina = pembinaRepository.findByNik(nikUser);
        }
        Specification<Surat> specification = Specification.where(null);
        if (pembina!=null){
            specification = specification.and(new SuratSpecification("kodePembina","=",pembina.getKodePembina()));
        }
        Page<Surat> suratPage = suratRepository.findAll(specification,pageable);
        return  suratPage;
    }

    public void save(Surat surat) throws Exception {
        Perusahaan perusahaan = perusahaanRepository.findByNpp(surat.getNpp());
        if (perusahaan==null){
            throw new Exception("Perusahaan not found");
        }
        surat.setNamaPerusahaan(perusahaan.getNama());
        suratRepository.save(surat);
    }

    public void save(List<Surat> surat) throws Exception {
        List<String> listNpp=new ArrayList<>();
        for (Surat surat1:surat){
            listNpp.add(surat1.getNpp());
        }
        List<Perusahaan> perusahaanList = perusahaanRepository.findByNppIn(listNpp);
        if (perusahaanList.size()!=listNpp.size()){
            String listNppFail = "";
            for (Perusahaan perusahaan :perusahaanList){
                if(listNpp.contains(perusahaan.getNpp())){
                    listNppFail.concat(perusahaan.getNpp()+",");
                }
            }
            throw new Exception("Npp Tidak Valid :"+listNppFail);
        }
        suratRepository.saveAll(surat);
    }
}
