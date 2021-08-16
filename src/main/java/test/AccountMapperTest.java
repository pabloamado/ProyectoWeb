package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import entity.Account;
import mapper.AccountMapper;
import mock.DaoMock;

class AccountMapperTest {

	private DaoMock dao=new DaoMock();
	private AccountMapper accountMapper=new AccountMapper(dao, null);
	
	@Test
	final void testSaveAccountSuccess() {
		dao.setSaved(true);
		boolean result=accountMapper.saveAccount(new Account());
		assertTrue(result);
	}
	
	@Test
	final void testSaveAccountFailed() {
		dao.setSaved(false);
		boolean result=accountMapper.saveAccount(new Account());
		assertFalse(result);
		
	}

	@Test
	final void testUpdateAccountSuccess() {
		dao.setSaved(true);
		boolean result=accountMapper.updateAccount(new Account());
		assertTrue(result);
		
	}
	
	@Test
	final void testUpdateAccountFailed() {
		dao.setSaved(false);
		boolean result=accountMapper.updateAccount(new Account());
		assertFalse(result);
		
	}

	@Test
	final void testDeleteAccountSuccess() {
		dao.setSaved(true);
		boolean result=accountMapper.deleteAccount(1);
		assertTrue(result);
		
	}
	
	@Test
	final void testDeleteAccountFailed() {
		dao.setSaved(false);
		boolean result=accountMapper.deleteAccount(2);
		assertFalse(result);
		
	}

	@Test
	final void testUpdateNicknameSuccess() {
		dao.setSaved(true);
		boolean result=accountMapper.updateNickname("nickname",2);
		assertTrue(result);
		
	}
	
	@Test
	final void testUpdateNicknameFailed() {
		dao.setSaved(false);
		boolean result=accountMapper.updateNickname("nickname",2);
		assertFalse(result);
		
	}

	@Test
	final void testUpdateMailSuccess() {
		dao.setSaved(true);
		boolean result=accountMapper.updateMail("pablo@gmail.com",2);
		assertTrue(result);
		
	}
	
	@Test
	final void testUpdateMailFailed() {
		dao.setSaved(false);
		boolean result=accountMapper.updateMail("pablo@gmail.com",2);
		assertFalse(result);
		
	}

	@Test
	final void testUpdatePasswordSuccess() {
		dao.setSaved(true);
		boolean result=accountMapper.updatePassword("asdasdasd123","pablo@gmail.com",0);
		assertTrue(result);
		
	}
	
	@Test
	final void testUpdatePasswordFailed() {
		dao.setSaved(false);
		boolean result=accountMapper.updatePassword("asdasdasd123","pablo@gmail.com",1);
		assertFalse(result);
		
	}

}
