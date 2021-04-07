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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        Pageable pageable = PageRequest.of(suratReqObj.getPage(),suratReqObj.getSize(),Sort.Direction.ASC, "id");
        Pembina pembina = null;
        if(nikUser!=null){
            pembina = pembinaRepository.findByNik(nikUser);
        }
        Specification<Surat> specification = Specification.where(new SuratSpecification("isDeleted","=","0"));
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
        List<Surat> suratDistinctNpp = surat.stream()
                .filter(distinctByKey(p -> p.getNpp()))
                .collect(Collectors.toList());
        for (Surat nppSurat:suratDistinctNpp){
            listNpp.add(nppSurat.getNpp());
        }
        List<Perusahaan> perusahaanList = perusahaanRepository.findByNppIn(listNpp);
        if (perusahaanList.size()!=listNpp.size()){
            String listNppFail = "";
            for (Perusahaan perusahaan :perusahaanList){
                if(-1==listNpp.indexOf(perusahaan.getNpp())){
                    listNppFail.concat(perusahaan.getNpp()+",");
                }
            }
            throw new Exception("Npp Tidak Valid :"+listNppFail);
        }
        List<Surat> suratList = new ArrayList<>();
        for(Surat surat1:surat){
            Perusahaan perusahaan = findByNppIsIn(perusahaanList,surat1.getNpp());
            surat1.setNamaPerusahaan(perusahaan.getNama());
            suratList.add(surat1);
        }
        suratRepository.saveAll(suratList);
    }


    public static Perusahaan findByNppIsIn(List<Perusahaan> listPerusahaan, String npp) {
        return listPerusahaan.stream().filter(perusahaan -> npp.equals(perusahaan.getNpp())).findFirst().orElse(null);
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
