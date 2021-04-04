package com.bpjsk.monitor.repository;

import com.bpjsk.monitor.model.Perusahaan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerusahaanRepository extends JpaRepository<Perusahaan,Long> {

    Page<Perusahaan> findAll(Specification<Perusahaan> specification, Pageable pageable);

    Perusahaan findByNpp(String npp);
}
