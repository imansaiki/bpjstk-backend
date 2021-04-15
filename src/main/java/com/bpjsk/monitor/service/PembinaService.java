package com.bpjsk.monitor.service;

import com.bpjsk.monitor.exception.CustomException;
import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.User;
import com.bpjsk.monitor.repository.PembinaRepository;
import com.bpjsk.monitor.repository.UserRepository;
import com.bpjsk.monitor.repository.UserRoleRepository;
import com.bpjsk.monitor.requestobject.PembinaReqObj;
import com.bpjsk.monitor.specification.PembinaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PembinaService {
    @Autowired
    PembinaRepository pembinaRepository;

    @Autowired
    UserRepository userRepository;

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
        if(pembinaReqObj.getNipLk()!=null){
            specification= specification.and(new PembinaSpecification("nip","like", pembinaReqObj.getNipLk()));
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
        if(pembinaReqObj.getNamaLk()!=null){
            specification= specification.and(new PembinaSpecification("nama","like", pembinaReqObj.getNamaLk()));
        }
        return pembinaRepository.findAll(specification,pageable);
    }

    @Transactional
    public Pembina deletePembina(Long id) throws CustomException {
        Pembina pembina = pembinaRepository.findById(id).orElse(null);
        if(pembina==null){
            throw new CustomException("Delete Request id Not Valid", HttpStatus.BAD_REQUEST);
        }
        pembina.setIsDeleted(1);
        pembinaRepository.save(pembina);
        User user = userRepository.findByUsername(pembina.getNip());
        if(user==null){
            throw new CustomException("Delete failed,Account not found",HttpStatus.BAD_REQUEST);
        }
        user.setIsActive(0);
        userRepository.save(user);
        return pembina;
    }
}
