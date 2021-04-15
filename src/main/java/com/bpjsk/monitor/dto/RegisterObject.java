package com.bpjsk.monitor.dto;

import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.User;
import com.bpjsk.monitor.model.UserRole;
import lombok.Data;
import sun.security.util.Pem;

import javax.persistence.Column;


@Data
public class RegisterObject {

    private String username;
    private String password;
    private Integer isActive;
    private Integer roleId;
    private String nip;
    private String kodePembina;
    private String nama;
    private String alamat;
    private String kota;
    private String telepon;
    private String email;

    public User getUser(){
        User user = new User();
        user.setPassword(this.password);
        user.setIsActive(this.isActive);
        user.setUsername(this.username);
        UserRole userRole = new UserRole();
        userRole.setRoleId(this.roleId);
        user.setRole(userRole);
        return user;
    }
    public Pembina getPembina(){
        Pembina pembina = new Pembina();
        pembina.setNama(this.nama);
        pembina.setEmail(this.email);
        pembina.setAlamat(this.alamat);
        pembina.setKodePembina(this.kodePembina);
        pembina.setKota(this.kota);
        pembina.setNip(this.nip);
        pembina.setTelepon(this.telepon);
        return pembina;
    }
}
