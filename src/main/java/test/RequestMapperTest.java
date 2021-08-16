package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import mapper.RequestMapper;
import mock.DaoMock;

class RequestMapperTest {
	
	private DaoMock dao=new DaoMock();
	private RequestMapper requestMapper=new RequestMapper(dao, null);
	
	@Test
	final void testSendRequestSuccess() {
		dao.setSaved(true);
		boolean result=requestMapper.sendRequest(1, 2);
		assertTrue(result);
		
	}
	
	@Test
	final void testSendRequestFailed() {
		dao.setSaved(false);
		boolean result=requestMapper.sendRequest(1, 2);
		assertFalse(result);
	}

	@Test
	final void testSendAcceptRejectRequestSuccess() {
		dao.setSaved(true);
		boolean result=requestMapper.sendRequest(1, 2);
		assertTrue(result);
		
	}
	
	@Test
	final void testSendAcceptRejectRequestFailed() {
		dao.setSaved(false);
		boolean result=requestMapper.sendRequest(1, 2);
		assertFalse(result);
		
	}

	@Test
	final void testDeleteFriendSuccess() {
		dao.setSaved(true);
		boolean result=requestMapper.sendRequest(1, 2);
		assertTrue(result);
		
	}
	
	@Test
	final void testDeleteFriendFailed() {
		dao.setSaved(false);
		boolean result=requestMapper.sendRequest(1, 2);
		assertFalse(result);
		
	}

}
