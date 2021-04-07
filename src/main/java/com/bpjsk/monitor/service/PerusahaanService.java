package com.bpjsk.monitor.service;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.Perusahaan;
import com.bpjsk.monitor.repository.PembinaRepository;
import com.bpjsk.monitor.repository.PerusahaanRepository;
import com.bpjsk.monitor.requestobject.PerusahaanReqObj;
import com.bpjsk.monitor.specification.PerusahaanSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerusahaanService {

    @Autowired
    PerusahaanRepository perusahaanRepository;

    @Autowired
    PembinaRepository pembinaRepository;

    public Page<Perusahaan> getAll(PerusahaanReqObj perusahaanReqObj, String nikUser) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (perusahaanReqObj.getSort()!=null){
            if (perusahaanReqObj.getSort().equalsIgnoreCase("DESC")){
                sort = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest.of(perusahaanReqObj.getPage(),perusahaanReqObj.getSize(), Sort.Direction.ASC, "id");
        Pembina pembina = null;
        if(nikUser!=null){
            pembina = pembinaRepository.findByNik(nikUser);
        }
        Specification<Perusahaan> specification = Specification.where(new PerusahaanSpecification("isDeleted","=","0"));
        if (pembina!=null){
            specification = specification.and(new PerusahaanSpecification("kodePembina","=",pembina.getKodePembina()));
        }
        if (perusahaanReqObj.getNamaLk()!=null){
            specification = specification.and(new PerusahaanSpecification("nama","like",perusahaanReqObj.getNamaLk()));
        }
        if (perusahaanReqObj.getKotaLk()!=null){
            specification = specification.and(new PerusahaanSpecification("kota","like",perusahaanReqObj.getKotaLk()));
        }
        if (perusahaanReqObj.getNppLk()!=null){
            specification = specification.and(new PerusahaanSpecification("npp","like",perusahaanReqObj.getNppLk()));
        }
        return perusahaanRepository.findAll(specification,pageable);
    }

    public void save(Perusahaan perusahaan) throws Exception {
        Pembina pembina = null;
        if(perusahaan.getKodePembina()!=null){
            pembina= pembinaRepository.findByKodePembina(perusahaan.getKodePembina());
        }

        if (pembina==null){
            throw new Exception("Pembina not found");
        }
        perusahaanRepository.save(perusahaan);
    }

    public void save(List<Perusahaan> perusahaanList) {
        List<Perusahaan> perusahaanList1 = new ArrayList<>();
        for (Perusahaan perusahaan :perusahaanList){
            perusahaan.setKodePembina(null);
            perusahaanList1.add(perusahaan);
        }
        perusahaanRepository.saveAll(perusahaanList1);
    }
}
