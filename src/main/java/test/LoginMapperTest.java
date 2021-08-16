package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import entity.Account;
import mapper.LoginMapper;
import mock.DaoMock;

class LoginMapperTest {

	private DaoMock dao=new DaoMock();
	private LoginMapper loginMapper=new LoginMapper(dao);
	private Account account=new Account();
	@Test
	final void testUpdateSuccessAccountAttempts() {
		dao.setSaved(true);
		boolean result=loginMapper.updateAccountAttempts(account);
		assertTrue(result);
		
	}
	
	@Test
	final void testUpdateFailedAccountAttempts() {
		dao.setSaved(false);
		boolean result=loginMapper.updateAccountAttempts(account);
		assertFalse(result);
	}

	@Test
	final void testBlockAccountSuccess() {
		dao.setSaved(true);
		boolean result=loginMapper.blockAccount(account);
		assertTrue(result);
		
	}
	
	@Test
	final void testBlockAccountFailed() {
		dao.setSaved(false);
		boolean result=loginMapper.blockAccount(account);
		assertFalse(result);
		
	}

}
