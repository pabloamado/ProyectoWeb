package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import entity.Account;
import entity.Request;
import handler.RequestHandler;
import mock.AccountMapperMock;
import mock.RequestMapperMock;

class RequestHandlerTest {

	private RequestMapperMock requestMapper=new RequestMapperMock(null, null);
	private AccountMapperMock accMapper=new AccountMapperMock(null, null);
	private RequestHandler handler=new RequestHandler(accMapper, requestMapper);
	@Test
	final void testGetUsersNotNull() {
		accMapper.setUsers(new ArrayList<Account>());
		assertNotNull(handler.getUsers(1));
	}
	
	@Test
	final void testGetUsersNull() {
		accMapper.setUsers(null);
		assertNull(handler.getRequests(0));
	}

	@Test
	final void testGetRequestsNotNull() {
		requestMapper.setRequests(new ArrayList<Request>());
		assertNotNull(handler.getRequests(1));
	}
	
	@Test
	final void testGetRequestsNull() {
		requestMapper.setRequests(null);
		assertNull(handler.getUsers(0));
	}

	@Test
	final void testGetFriendsNotNull() {
		requestMapper.setFriends(new ArrayList<Request>());
		assertNotNull(handler.getFriends(1));
	}
	
	@Test
	final void testGetFriendsNull() {
		requestMapper.setFriends(null);
		assertNull(handler.getFriends(0));
		
	}
	
	@Test
	final void testSendRequestSuccess() {
		requestMapper.setSuccess(true);
		assertTrue(handler.sendRequest(1, 2));
	}
	
	@Test
	final void testSendRequestFailed() {
		requestMapper.setSuccess(false);
		assertFalse(handler.sendRequest(1, 2));
	}

	@Test
	final void testAcceptRejectRequestSuccess() {
		requestMapper.setSuccess(true);
		assertTrue(handler.acceptRejectRequest(2, null));
	}
	
	@Test
	final void testAcceptRejectRequestFailed() {
		requestMapper.setSuccess(false);
		assertFalse(handler.acceptRejectRequest(2, null));
	}

	@Test
	final void testDeleteFriendSuccess() {
		requestMapper.setSuccess(true);
		assertTrue(handler.deleteFriend(1));
	}
	
	@Test
	final void testDeleteFriendFailed() {
		requestMapper.setSuccess(false);
		assertFalse(handler.deleteFriend(1));
	}

}
