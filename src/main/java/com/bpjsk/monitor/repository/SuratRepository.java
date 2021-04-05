package com.bpjsk.monitor.repository;

import com.bpjsk.monitor.model.Surat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuratRepository extends JpaRepository<Surat,Long> {

    Page<Surat> findAll(Specification<Surat> specification, Pageable pageable);
}
