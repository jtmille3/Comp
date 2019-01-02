package com.sas.comp.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sas.comp.service.AuthenticationService;

@RestController
public class AuthenticationController {
	
	final AuthenticationService authenticationService = new AuthenticationService();

    @RequestMapping(
    		value = "/service/authentication", 
    		method = RequestMethod.POST, 
    		produces = "application/json"
    )
    public HttpEntity<?> authenticate(@RequestParam("password") String password) {
		if (authenticationService.authenticate(password)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }

}
