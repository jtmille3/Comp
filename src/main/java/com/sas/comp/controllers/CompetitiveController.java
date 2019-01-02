package com.sas.comp.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sas.comp.Cache;
import com.sas.comp.models.Competitive;

@RestController
public class CompetitiveController {
	
    @RequestMapping(
    		value = "/service/competitive", 
    		method = RequestMethod.GET, 
    		produces = "application/json"
    )
    public HttpEntity<Competitive> competitive() {
    	Competitive competitive = Cache.getCache().getCompetitive();
        ResponseEntity<Competitive> response = new ResponseEntity<Competitive>(competitive, HttpStatus.OK);
        return response;
    }

}
