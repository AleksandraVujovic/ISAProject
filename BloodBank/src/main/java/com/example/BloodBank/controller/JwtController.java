package com.example.BloodBank.controller;

import com.example.BloodBank.Utils.JwtUtil;
import com.example.BloodBank.model.AuthRequest;
import com.example.BloodBank.model.NotActivatedCustomer;
import com.example.BloodBank.service.EmailSenderService;
import com.example.BloodBank.service.HeadAdminService;
import com.example.BloodBank.service.NotActivatedCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private NotActivatedCustomerService notActivatedUserService;
    @Autowired
    private HeadAdminService headAdminService;

    @GetMapping("/")
    public String weclcome() {
        return  "Welcome";
    }
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        try{
            Authentication a = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName()
                            ,authRequest.getPassword())
            );
        } catch (Exception ex){
            throw new Exception("Invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
    @PostMapping("/registerNotActivated")
    public NotActivatedCustomer registerNotActivated(@RequestBody NotActivatedCustomer user) throws Exception{
        try{
            notActivatedUserService.Create(user);
            return user;
        } catch (Exception ex) {
            throw new Exception("Invalid info");
        }
    }
    @GetMapping("/activate/{activationCode}")
    public ResponseEntity<Boolean> activateUser(@PathVariable("activationCode") String activationCode) throws Exception{
        try {
            Boolean retVal = notActivatedUserService.Activate(activationCode);
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } catch (Exception ex) {
            throw new Exception("Activation failed");
        }
    }
}
