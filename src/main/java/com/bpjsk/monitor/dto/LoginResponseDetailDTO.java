package com.bpjsk.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDetailDTO {
    String name;
    String username;
    String token;
    String role;
    Date loginTime;
}
