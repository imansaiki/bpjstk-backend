package com.bpjsk.monitor.repository;

import com.bpjsk.monitor.model.Perusahaan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerusahaanRepository extends JpaRepository<Perusahaan,Long> {
}
