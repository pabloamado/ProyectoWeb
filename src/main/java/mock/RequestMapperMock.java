package mock;

import java.util.ArrayList;
import dao.Dao;
import entity.Request;
import entity.RequestState;
import helper.Helper;
import mapper.RequestMapper;

public class RequestMapperMock extends RequestMapper{

	private boolean success;
	private ArrayList<Request> requests;
	private ArrayList<Request>friends;
	
	public RequestMapperMock(Dao dao, Helper helper) {
		super(dao, helper);
	}

	public void setSuccess(boolean success) {
		this.success=success;
	}
	
	public void setRequests(ArrayList<Request> requests) {
		this.requests = requests;
	}
	
	public void setFriends(ArrayList<Request> friends) {
		this.friends = friends;
	}

	@Override
	public boolean deleteFriend(int requestId) {
		
		return success;
	}
	
	@Override
	public ArrayList<Request> getFriends(int accountId) {
	
		return friends;
	}
	
	@Override
	public ArrayList<Request> getRequests(int accountId) {
		
		return requests;
	}
	
	@Override
	public boolean sendAcceptRejectRequest(int requestId, RequestState flagRequest) {
	
		return success;
	}
	
	@Override
	public boolean sendRequest(int accountId, int userId) {
		
		return success;
	}
	
}
