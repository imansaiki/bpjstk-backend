package com.bpjsk.monitor.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDetailDTO {
    String token;
    String role;
    Date loginTime;
}
