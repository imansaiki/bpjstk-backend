package com.bpjsk.monitor.repository;

import com.bpjsk.monitor.model.Pembina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PembinaRepository extends JpaRepository<Pembina,Long> {

    Pembina findByNip(String username);

    Page<Pembina> findAll(Specification<Pembina> specification, Pageable pageable);

    Pembina findByKodePembina(String kodePembina);
}
