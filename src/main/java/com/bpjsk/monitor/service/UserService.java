package com.bpjsk.monitor.service;

import com.bpjsk.monitor.config.UserPrincipal;
import com.bpjsk.monitor.dto.LoginResponseDetailDTO;
import com.bpjsk.monitor.dto.RegisterObject;
import com.bpjsk.monitor.exception.CustomException;
import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.User;
import com.bpjsk.monitor.repository.PembinaRepository;
import com.bpjsk.monitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PembinaRepository pembinaRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
    @Transactional
    public void registerAdmin(RegisterObject registerObject) throws Exception {
        if (registerObject.getUsername()==null||
                registerObject.getPassword()==null
        ){
            throw new Exception("Lengkapi field yang diminta");
        }
        String password = passwordEncoder.encode(registerObject.getPassword());
        registerObject.setPassword(password);
        registerObject.setRoleId((long) 1);
        userRepository.save(registerObject.getUser());
        //pembinaRepository.save(registerObject.getPembina());
    }
    @Transactional
    public void registerPembina(RegisterObject registerObject) throws Exception {
        if (
                registerObject.getNip()==null|| registerObject.getPassword()==null||
                registerObject.getNama()==null|| registerObject.getKodePembina()==null
        ){
            throw new Exception("Lengkapi field yang diminta");
        }
        String password = passwordEncoder.encode(registerObject.getPassword());
        registerObject.setPassword(password);
        registerObject.setRoleId((long) 2);
        registerObject.setUsername(registerObject.getNip());
        Pembina pembina = null;
        try {
            pembina = pembinaRepository.save(registerObject.getPembina());
        }catch (DataIntegrityViolationException e){
            System.out.println(e.getMessage());
            throw new CustomException("NIP atau Kode Pembina telah digunakan", HttpStatus.BAD_REQUEST,e);
        }
        User user = userRepository.findByUsername(pembina.getNip());
        User newUser = registerObject.getUser();
        if (user!=null){
            newUser.setId(user.getId());
        }
        try {
            userRepository.save(newUser);
        }catch (DataIntegrityViolationException e){
            System.out.println(e.getMessage());
            throw new CustomException("Username telah digunakan", HttpStatus.BAD_REQUEST);
        }

    }
    public LoginResponseDetailDTO signIn(String username, String password){
        Authentication auth = new UsernamePasswordAuthenticationToken(username,password);
        authenticationManager.authenticate(auth);
        User user = userRepository.findByUsername(username);
        Pembina pembina = pembinaRepository.findByNip(username);
        String originalInput = username+":"+password;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        LoginResponseDetailDTO loginResponseDetailDTO = new LoginResponseDetailDTO();
        loginResponseDetailDTO.setUsername(username);
        loginResponseDetailDTO.setLoginTime(new Date());
        loginResponseDetailDTO.setName(pembina!=null?pembina.getNama():"");
        loginResponseDetailDTO.setRole(user.getRole().getRole());
        loginResponseDetailDTO.setToken(encodedString);


        return loginResponseDetailDTO;
    }
}
