package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import factory.HandlerFactory;
import handler.LoginHandler;
import response.LoginResponse;

@Path("log")
public class LoginService {

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(String loginJson) {
		
		Gson gson = new Gson();
		LoginHandler handler = HandlerFactory.createLoginHandler();

		JsonObject jsonObj = gson.fromJson(loginJson, JsonObject.class);
		String email = jsonObj.get("userMail").getAsString().toLowerCase();
		String enteredPassword = jsonObj.get("userPassword").getAsString();
	
		LoginResponse response=handler.getLoginResponse(email, enteredPassword);
		
		return Response.ok().entity(gson.toJson(response)).build();

	}

}
