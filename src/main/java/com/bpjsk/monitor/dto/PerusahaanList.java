package com.bpjsk.monitor.dto;

import com.bpjsk.monitor.model.Perusahaan;
import lombok.Data;

import java.util.List;

@Data
public class PerusahaanList {
    private List<Perusahaan> perusahaans;
}
