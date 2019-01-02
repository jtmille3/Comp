package com.sas.comp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BaseService {

	@Autowired
    JdbcTemplate jdbcTemplate;
    
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
}
