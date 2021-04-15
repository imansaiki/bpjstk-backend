package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.dto.LoginResponseDetailDTO;
import com.bpjsk.monitor.dto.RegisterObject;
import com.bpjsk.monitor.exception.CustomException;
import com.bpjsk.monitor.model.Pembina;
import com.bpjsk.monitor.model.User;
import com.bpjsk.monitor.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(path="/user")
public class UserAPI {
    @Autowired
    UserService userService;

    @RequestMapping(value="/signin", method= RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login (@RequestBody User user) {
        HashMap response = new HashMap<String,Object>();
        LoginResponseDetailDTO data = userService.signIn(user.getUsername(), user.getPassword());
        response.put("message","Login Success");
        response.put("data",data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value="/detail", method= RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getDetail (Authentication authentication, Principal principal, HttpServletRequest request) {
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getName());
        System.out.println(principal);
        System.out.println(request.isUserInRole("ROLE_ADMIN"));
        System.out.println(request.getUserPrincipal().getName());
        HashMap response = new HashMap<String,Object>();
        response.put("message","Get Detail Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/register", method= RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register (@RequestBody RegisterObject registerObject) {
        HashMap response = new HashMap<String,Object>();
        response.put("message","Register Success");
        try {
            userService.registerPembina(registerObject);
        }catch (CustomException e){
            response.put("message",e.getMessage());
            log.error(e.getOriginalException().getMessage());
            return new ResponseEntity<>(response, e.getHttpStatus());
        }catch (DataIntegrityViolationException e){
            response.put("message","Terdapat kesalahan saat memasukan data");
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            response.put("message",e.getMessage());
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @RequestMapping(value="/registerAdmin", method= RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> registerAdmin(@RequestBody RegisterObject registerObject) {
        HashMap response = new HashMap<String,Object>();
        response.put("message","Register Success");
        try {
            userService.registerAdmin(registerObject);
        }catch (CustomException e){
            response.put("message",e.getMessage());
            log.error(e.getMessage());
            return new ResponseEntity<>(response, e.getHttpStatus());
        }catch (DataIntegrityViolationException e){
            response.put("message","Terdapat error saat memasukan data");
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            response.put("message",e.getMessage());
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
