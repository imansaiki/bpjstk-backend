package com.bpjsk.monitor.service;

import com.bpjsk.monitor.model.Perusahaan;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PerusahaanService {
    public Page<Perusahaan> getAll(Integer start, Integer size, String asc, String id, String orElse, String orElse1, String orElse2, String orElse3) {
        return null;
    }
}
