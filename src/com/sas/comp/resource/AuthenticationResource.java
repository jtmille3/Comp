package com.sas.comp.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sas.comp.service.AuthenticationService;

@Path("authentication")
public class AuthenticationResource {

	final AuthenticationService authenticationService = new AuthenticationService();

	@POST
	public Response authenticate(@QueryParam("password") final String password) {
		if (authenticationService.authenticate(password)) {
			return Response.ok().build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
