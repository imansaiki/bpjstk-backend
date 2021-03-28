package com.bpjsk.monitor.repository;

import com.bpjsk.monitor.model.Pembina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PembinaRepository extends JpaRepository<Pembina,Long> {
    Pembina findByNik(String username);
}
