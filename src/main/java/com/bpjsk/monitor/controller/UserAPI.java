package com.bpjsk.monitor.controller;

import com.bpjsk.monitor.DTO.LoginResponseDetailDTO;
import com.bpjsk.monitor.model.User;
import com.bpjsk.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path="/user")
public class UserAPI {
    @Autowired
    UserService userService;

    @RequestMapping(value="/signin", method= RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login (@RequestBody User user) {
        HashMap response = new HashMap<String,Object>();
        userService.signIn(user.getUsername(), user.getPassword());
        String originalInput = user.getUsername()+":"+user.getPassword();
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        response.put("message","Login Success");
        response.put("token",encodedString);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value="/detail", method= RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getDetail (Authentication authentication, Principal principal, HttpServletRequest request) {
        //System.out.println(authentication.getPrincipal());
        //System.out.println(authentication.getDetails());
        //System.out.println(authentication.getCredentials());
        //System.out.println(authentication.getAuthorities());
        //System.out.println(authentication.getName());
        //System.out.println(principal);
        //System.out.println(request.isUserInRole("ROLE_ADMIN"));
        //System.out.println(request.getUserPrincipal());
        HashMap response = new HashMap<String,Object>();
        response.put("message","Get Detail Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/register", method= RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register (@RequestBody User user) {
        HashMap response = new HashMap<String,Object>();
        userService.registerUser(user);
        response.put("message","Register Success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
