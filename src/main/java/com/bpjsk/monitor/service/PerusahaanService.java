package com.bpjsk.monitor.service;

import com.bpjsk.monitor.exception.CustomException;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerusahaanService {

    @Autowired
    PerusahaanRepository perusahaanRepository;

    @Autowired
    PembinaRepository pembinaRepository;

    public Page<Perusahaan> getAll(PerusahaanReqObj perusahaanReqObj) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (perusahaanReqObj.getSort()!=null){
            if (perusahaanReqObj.getSort().equalsIgnoreCase("DESC")){
                sort = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest.of(perusahaanReqObj.getPage(),perusahaanReqObj.getSize(), Sort.Direction.ASC, "id");
        Specification<Perusahaan> specification = Specification.where(new PerusahaanSpecification("isDeleted","=","0"));
        if (perusahaanReqObj.getNamaLk()!=null){
            specification = specification.and(new PerusahaanSpecification("nama","like",perusahaanReqObj.getNamaLk()));
        }
        if (perusahaanReqObj.getKotaLk()!=null){
            specification = specification.and(new PerusahaanSpecification("kota","like",perusahaanReqObj.getKotaLk()));
        }
        if (perusahaanReqObj.getKodePembinaLk()!=null){
            specification = specification.and(new PerusahaanSpecification("kodePembina","like",perusahaanReqObj.getKodePembinaLk()));
        }
        if (perusahaanReqObj.getNppLk()!=null){
            specification = specification.and(new PerusahaanSpecification("npp","like",perusahaanReqObj.getNppLk()));
        }
        if (perusahaanReqObj.getNppEq()!=null){
            specification = specification.and(new PerusahaanSpecification("npp","=",perusahaanReqObj.getNppEq()));
        }
        return perusahaanRepository.findAll(specification,pageable);
    }

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
            pembina = pembinaRepository.findByNip(nikUser);
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
        if (perusahaanReqObj.getKodePembinaLk()!=null){
            specification = specification.and(new PerusahaanSpecification("kodePembina","like",perusahaanReqObj.getKodePembinaLk()));
        }
        if (perusahaanReqObj.getNppLk()!=null){
            specification = specification.and(new PerusahaanSpecification("npp","like",perusahaanReqObj.getNppLk()));
        }
        if (perusahaanReqObj.getNppEq()!=null){
            specification = specification.and(new PerusahaanSpecification("npp","=",perusahaanReqObj.getNppEq()));
        }
        return perusahaanRepository.findAll(specification,pageable);
    }

    @Transactional
    public void save(Perusahaan perusahaan) throws Exception {
        Pembina pembina = null;
        Boolean useCode = false;
        if(perusahaan.getKodePembina()!=null&&!perusahaan.getKodePembina().isEmpty()){
            useCode=true;
            pembina= pembinaRepository.findByKodePembina(perusahaan.getKodePembina());
        }
        if (pembina==null&& useCode==true){
            throw new Exception("Kode Pembina Tidak Ditemukan");
        }
        perusahaanRepository.save(perusahaan);
    }

    @Transactional
    public void save(List<Perusahaan> perusahaanList) {
        List<Perusahaan> perusahaanList1 = new ArrayList<>();
        for (Perusahaan perusahaan :perusahaanList){
            perusahaan.setKodePembina(null);
            perusahaanList1.add(perusahaan);
        }
        perusahaanRepository.saveAll(perusahaanList1);
    }

    @Transactional
    public Perusahaan deletePerusahaan(Long id) throws Exception {
        Perusahaan perusahaan = perusahaanRepository.findById(id).orElse(null);
        if(perusahaan==null){
            throw new CustomException("Delete Request id Not Valid", HttpStatus.BAD_REQUEST);
        }
        perusahaan.setIsDeleted(1);
        perusahaanRepository.save(perusahaan);
        return perusahaan;
    }
}
