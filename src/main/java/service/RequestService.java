package service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Account;
import entity.Request;
import entity.RequestState;
import factory.HandlerFactory;
import handler.RequestHandler;

@Path("request")
public class RequestService {

	@POST
	@Path("getUsers")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response getUsers(String jsonId) {
		
		Gson gson = new Gson();
		RequestHandler handler=HandlerFactory.createRequestHandler();
		
		JsonObject jsonObj = gson.fromJson(jsonId, JsonObject.class);
		int accountId = jsonObj.get("accountId").getAsInt();
		 
		ArrayList<Account>users=handler.getUsers(accountId);
		
	return Response.status(Response.Status.OK).entity(gson.toJson(users)).build();
	}
	
	@POST
	@Path("getFriends")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response getFriends(String jsonId) {
		
		Gson gson = new Gson();
		RequestHandler handler=HandlerFactory.createRequestHandler();
		
		JsonObject jsonObj = gson.fromJson(jsonId, JsonObject.class);
		int accountId = jsonObj.get("accountId").getAsInt();
		 
		ArrayList<Request>friends=handler.getFriends(accountId);
		
	return Response.status(Response.Status.OK).entity(gson.toJson(friends)).build();
	}
	
	@POST
	@Path("getRequests") 
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response getRequests(String jsonId) {
		
		Gson gson = new Gson();
		RequestHandler handler=HandlerFactory.createRequestHandler();
		JsonObject jsonObj = gson.fromJson(jsonId, JsonObject.class);
		int accountId = jsonObj.get("accountId").getAsInt();
		 
		ArrayList<Request>requests=handler.getRequests(accountId);
		
	return Response.status(Response.Status.OK).entity(gson.toJson(requests)).build();
	}
	
	@POST
	@Path("sendRequest")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response sendRequest(String jsonIds) {
		
		Gson gson = new Gson();
		RequestHandler handler=HandlerFactory.createRequestHandler();
		
		JsonObject jsonObj = gson.fromJson(jsonIds, JsonObject.class);
		int accountId = jsonObj.get("accountId").getAsInt();
		int userId = jsonObj.get("userId").getAsInt();
		
		boolean sended=handler.sendRequest(accountId,userId);
		
	return Response.status(Response.Status.OK).entity(gson.toJson(sended)).build();
	}
	
	@POST
	@Path("acceptRequest")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response acceptRequest(String jsonId) {
		
		Gson gson = new Gson();
		RequestHandler handler=HandlerFactory.createRequestHandler();
		JsonObject jsonObj = gson.fromJson(jsonId, JsonObject.class);
		int requestId= jsonObj.get("requestId").getAsInt();
		
		boolean accepted=handler.acceptRejectRequest(requestId,RequestState.ACCEPTED);
		
	return Response.status(Response.Status.OK).entity(gson.toJson(accepted)).build();
	}
	
	@POST
	@Path("rejectRequest") 
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response rejectRequest(String jsonId) {
		
		Gson gson = new Gson();
		RequestHandler handler=HandlerFactory.createRequestHandler();
		JsonObject jsonObj = gson.fromJson(jsonId, JsonObject.class);
		int requestId= jsonObj.get("requestId").getAsInt();
		
		boolean rejected=handler.acceptRejectRequest(requestId,RequestState.REJECTED);
		
	return Response.status(Response.Status.OK).entity(gson.toJson(rejected)).build();
	}
	
	@POST
	@Path("deleteFriend") 
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response deleteFriend(String jsonId) {
		
		Gson gson = new Gson();
		RequestHandler handler=HandlerFactory.createRequestHandler();
		
		JsonObject jsonObj = gson.fromJson(jsonId, JsonObject.class);
		int requestId = jsonObj.get("requestId").getAsInt();
		
		boolean deleted=handler.deleteFriend(requestId);
		
	return Response.status(Response.Status.OK).entity(gson.toJson(deleted)).build();
	}
	
}
