package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import factory.HandlerFactory;
import handler.AccountHandler;
import response.NicknameResponse;

@Path("configuration")
public class ConfigurationService {

	// RECIBIR LOS PARAMETROS EN EL JAVASCRIPT
	@POST
	@Path("updateNickname")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateNickname(String dataJson) {

		Gson gson = new Gson();
		AccountHandler handler = HandlerFactory.createAccountHandler();

		JsonObject jsonObj = gson.fromJson(dataJson, JsonObject.class);
		String nick = jsonObj.get("nickname").getAsString();
		int id = jsonObj.get("id").getAsInt();
		
		NicknameResponse  response=handler.getUpdateNicknameResponse(nick,id);
		
		return Response.status(Response.Status.OK).entity(gson.toJson(response)).build();
	}

	@POST
	@Path("updateMail")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMail(String dataJson) {
		
		Gson gson = new Gson();
		AccountHandler handler = HandlerFactory.createAccountHandler();
		
		JsonObject jsonObj = gson.fromJson(dataJson, JsonObject.class);
		String newMail = jsonObj.get("mail").getAsString().toLowerCase();
		String repeatedNewMail = jsonObj.get("repeatedMail").getAsString().toLowerCase();
		String actualMail = jsonObj.get("actualMail").getAsString().toLowerCase();
		int accountId=jsonObj.get("accountId").getAsInt();
		
;		String responseMsg = handler.getMsgUpdateMail(newMail,repeatedNewMail, actualMail, accountId);

		return Response.status(Response.Status.OK).entity(gson.toJson(responseMsg)).build();
	}
	
	@POST
	@Path("updatePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePassword(String dataJson) {

		Gson gson = new Gson();
		AccountHandler handler = HandlerFactory.createAccountHandler();

		JsonObject jsonObj = gson.fromJson(dataJson, JsonObject.class);
		String newPass = jsonObj.get("newPassword").getAsString();
		String repeatedPass = jsonObj.get("repeatedNewPassword").getAsString();
		String actualPass = jsonObj.get("actualPassword").getAsString();
		int accountId = jsonObj.get("accountId").getAsInt();
	
		String responseMsg = handler.getMsgUpdatePassword(newPass, repeatedPass, actualPass, accountId);

		return Response.status(Response.Status.OK).entity(gson.toJson(responseMsg)).build();

	}

	@POST
	@Path("deleteAccount")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAccount(String idJson) {
		
		Gson gson = new Gson();
		AccountHandler handler = HandlerFactory.createAccountHandler();
		
		JsonObject jsonObj = gson.fromJson(idJson, JsonObject.class);
		boolean deleted = handler.deletedAccount(jsonObj.get("id").getAsInt());

		return Response.status(Response.Status.OK).entity(deleted).build();
	}
}
