package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dao.Dao;
import entity.Account;
import handler.LoginHandler;
import mock.AccountHandlerMock;
import mock.AccountValidatorMock;
import mock.LoginMapperMock;
import mock.MailSenderMock;
import response.LoginResponse;

class LoginHandlerTest {
	
	private LoginMapperMock loginMapper=new LoginMapperMock(new Dao());
	private AccountValidatorMock validator=new AccountValidatorMock();
	private AccountHandlerMock accHandler=new AccountHandlerMock(null,null,null,null);
	private MailSenderMock mailSender=new MailSenderMock();
	private Account account=new Account();
	private LoginHandler handler=new LoginHandler(loginMapper,validator,accHandler,mailSender);
	
	@Test
	final void testGetLoginResponseMailNotValidSuccessFalse() {
		String msg="Los datos ingresados no tiene el formato correcto,asegurese de ingresar una"
				+ " direccion de mail y contraseña validas.";
		validator.setValid(false);
		LoginResponse response=handler.getLoginResponse("ejemplo@gmail.com", "asdasd1");
		assertEquals(response.getMsg(),msg);
		assertFalse(response.isSuccess());
	}
	
	@Test
	final void testGetLoginResponseAccountNotExistedSuccessFalse() {
		String msg="No hay ninguna cuenta asociada al mail ingresado.";
		validator.setValid(true);
		accHandler.setAccount(null);
		LoginResponse response=handler.getLoginResponse("ejemplo@gmail.com", "asdasd1");
		assertEquals(response.getMsg(), msg);
		assertFalse(response.isSuccess());
	}
	
	@Test
	final void testGetLoginResponseAccountBlockedSuccessFalse() {
		String msg="La cuenta a la que intentas acceder ha sido bloqueada.";
		validator.setValid(true);
		account.setBlocked(true);
		accHandler.setAccount(account);
		loginMapper.setSuccess(true);
		LoginResponse response=handler.getLoginResponse("ejemplo@gmail.com", "asdasd1");
		assertEquals(response.getMsg(), msg);
		assertFalse(response.isSuccess());
	}
	
	@Test
	final void testGetLoginResponseAccountDeletedSuccessFalse() {
		String msg="La cuenta a la que intentas acceder ha sido borrada.";
		validator.setValid(true);
		account.setDeleted(true);
		accHandler.setAccount(account);
		LoginResponse response=handler.getLoginResponse("ejemplo@gmail.com", "asdasd1");
		assertEquals(response.getMsg(), msg);
		assertFalse(response.isSuccess());
	}
	
	@Test
	final void testGetLoginResponseDistinctPasswordsSuccessFalse() {
		String msg="La contraseña es erronea. "+ "Su usuario fue bloqueado.";
		validator.setValid(true);
		validator.setEqual(false);
		account.setAttempts(2);
		account.setPassword("1234sfaagf");
		account.setMail("ejemplo@gmail.com");
		accHandler.setAccount(account);
		loginMapper.setSuccess(true);
		LoginResponse response=handler.getLoginResponse("ejemplo@gmail.com", "asdasd1");
		assertEquals(response.getMsg(), msg);
		assertFalse(response.isSuccess());
	}
	@Test
	final void testGetLoginResponseAttemptsLeftSuccessFalse() {
		String msg="La contraseña es erronea. "+ "Le quedan 2 intentos.";
		validator.setValid(true);
		validator.setEqual(false);
		account.setAttempts(0);
		account.setPassword("1234sfaagf");
		account.setMail("ejemplo@gmail.com");
		accHandler.setAccount(account);
		loginMapper.setSuccess(true);
		LoginResponse response=handler.getLoginResponse("ejemplo@gmail.com", "asdasd1");
		assertEquals(response.getMsg(), msg);
		assertFalse(response.isSuccess());
		
	}
	
	@Test
	final void ResponseSuccessWithAccountAndMsgNull() {
		validator.setValid(true);
		validator.setEqual(true);
		account.setAttempts(0);
		account.setPassword("1234sfaagf");
		account.setMail("ejemplo@gmail.com");
		accHandler.setAccount(account);
		loginMapper.setSuccess(true);
		LoginResponse response=handler.getLoginResponse("ejemplo@gmail.com", "1234sfaagf");
		assertTrue(response.isSuccess());
		assertNotNull(response.getAccount());
		assertNull(response.getMsg());
		
	}
	
}
