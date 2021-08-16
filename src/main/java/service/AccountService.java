package service;

import java.time.LocalDate;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Account;
import factory.HandlerFactory;
import handler.AccountHandler;
import handler.PostHandler;
import response.GenericResponse;

@Path("account")
public class AccountService {

	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerAccount(String registerJson) {

		Gson gson = new Gson();
		Account account = new Account();
		AccountHandler handler = HandlerFactory.createAccountHandler();
		JsonObject jsonObj = gson.fromJson(registerJson, JsonObject.class);

		String name = jsonObj.get("userName").getAsString();
		String lastName = jsonObj.get("userLastName").getAsString();
		String stringDate = jsonObj.get("userBirthday").getAsString();
		LocalDate birthday = LocalDate.parse(stringDate);
		String mail = jsonObj.get("userMail").getAsString().toLowerCase();
		String password = jsonObj.get("userPassword").getAsString();
		String nickname = jsonObj.get("userNickname").getAsString();
		
		account.setName(name);
		account.setLastName(lastName);
		account.setBirthday(birthday);
		account.setMail(mail);
		account.setPassword(password);
		account.setNickName(nickname);
		
		String responseMsg=handler.getMsgRegisterAccount(account);
		
		return Response.status(Response.Status.OK).entity(gson.toJson(responseMsg)).build();
	}

	@POST
	@Path("sendForgotMail")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendForgotMail(String mailJson) {
		
		Gson gson = new Gson();
		AccountHandler handler =HandlerFactory.createAccountHandler();
		
		JsonObject jsonObj = gson.fromJson(mailJson, JsonObject.class);
		String mail = jsonObj.get("userMail").getAsString().toLowerCase();
		
		GenericResponse response=handler.getSendForgotMailResponse(mail);
		
		return Response.status(Response.Status.OK).entity(gson.toJson(response)).build();
	}
	
	@POST
	@Path("forgotPassword")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response forgotPassword(String dataJson) {
		
		Gson gson = new Gson();
		AccountHandler handler = HandlerFactory.createAccountHandler();
		
		JsonObject jsonObj = gson.fromJson(dataJson, JsonObject.class);
		String newPass=jsonObj.get("password").getAsString();
		String repeatedNewPass=jsonObj.get("repeatedPassword").getAsString();
		String mail=jsonObj.get("email").getAsString().toLowerCase();
		String code=jsonObj.get("userCode").getAsString();
		
		GenericResponse response=handler.getUpdatePassResponse
				(newPass, repeatedNewPass, mail, code);
		
		return Response.status(Response.Status.OK).entity(gson.toJson(response)).build();
	}
	
	@POST
	@Path("getFriendAccount") 
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response getFriendProfile(String jsonId) {
		
		Gson gson = new Gson();
		AccountHandler accountHandler=HandlerFactory.createAccountHandler();
		PostHandler postHandler = HandlerFactory.createPostHandler();
		
		JsonObject jsonObj = gson.fromJson(jsonId, JsonObject.class);
		int friendId = jsonObj.get("friendId").getAsInt();
		
		Account account=accountHandler.getFriendAccountWithId(friendId);
		
		if(account!=null) {
			account.setPostList(postHandler.getPosts(friendId));
		}
	
	return Response.status(Response.Status.OK).entity(gson.toJson(account)).build();
	}
		
}
