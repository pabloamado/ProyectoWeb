package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dao.Dao;
import entity.Account;
import handler.AccountHandler;
import helper.Helper;
import mock.AccountMapperMock;
import mock.AccountValidatorMock;
import mock.CodeMapperMock;
import mock.MailSenderMock;
import response.GenericResponse;
import response.NicknameResponse;

class AccountHandlerTest {

	private AccountValidatorMock validator=new AccountValidatorMock();
	private AccountMapperMock accountMapper=new AccountMapperMock(new Dao(),new Helper());
	private MailSenderMock mailSender=new MailSenderMock();
	private CodeMapperMock codeMapper=new CodeMapperMock(new Dao(),new Helper());
	private AccountHandler handler=new AccountHandler(accountMapper,codeMapper,validator,mailSender);
	
	@Test
	final void testGetMsgRegisterAccountInvalid() {
		String msg="Los datos ingresados no son validos, vuelve a intentarlo.";
		validator.setValid(false);
		String responseMsg=handler.getMsgRegisterAccount(new Account());
		assertEquals(msg,responseMsg);
	}
	
	@Test
	final void testGetMsgRegisterAccountSuccess() {
		String msg="Se registro exitosamente.";
		validator.setValid(true);
		accountMapper.setAccount(null);
		accountMapper.setSuccess(true);
		String responseMsg=handler.getMsgRegisterAccount(new Account());
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetMsgRegisterAccountFailed() {
		String msg="Ha ocurrido un error con el servidor mientras se intentaba registrar.";
		validator.setValid(true);
		accountMapper.setAccount(null);
		accountMapper.setSuccess(false);
		String responseMsg=handler.getMsgRegisterAccount(new Account());
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetMsgRegisterAccountExistedAndBlock() {
		String msg="Ya existe una cuenta asociada al mail introducido pero se encuentra bloqueada";
		validator.setValid(true);
		Account account=new Account();
		account.setBlocked(true);
		accountMapper.setAccount(account);
		String responseMsg=handler.getMsgRegisterAccount(new Account());
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetMsgRegisterAccountReactivateAccountSuccess() {
		String msg="Hemos reactivado su cuenta ya que previamente fue borrada.";
		validator.setValid(true);
		Account account=new Account();
		account.setDeleted(true);
		accountMapper.setAccount(account);
		accountMapper.setSuccess(true);
		String responseMsg=handler.getMsgRegisterAccount(new Account());
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetMsgRegisterAccountReactivateAccountFailed() {
		String msg="Ha ocurrido un error al intentar reactivar su cuenta.";
		validator.setValid(true);
		Account account=new Account();
		account.setDeleted(true);
		accountMapper.setAccount(account);
		accountMapper.setSuccess(false);
		String responseMsg=handler.getMsgRegisterAccount(new Account());
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetMsgRegisterAccountAlreadyExists() {
		String msg="Ya existe una cuenta asociada al mail introducido.";
		validator.setValid(true);
		Account account=new Account();
		accountMapper.setAccount(account);
		String responseMsg=handler.getMsgRegisterAccount(new Account());
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetUpdateNicknameResponseNickInvalid() {
	String msg="La longitud del apodo no es valido";
	validator.setValid(false);
	NicknameResponse response=handler.getUpdateNicknameResponse("nickname", 1);
	assertEquals(msg,response.getMsg());
	assertNull(response.getNickname());
	}
	
	@Test
	final void testGetUpdateNicknameResponseUpdateFailed() {
	String msg="No se ha podido cambiar el apodo";
	validator.setValid(true);
	accountMapper.setSuccess(false);
	NicknameResponse response=handler.getUpdateNicknameResponse("nickname", 1);
	assertEquals(msg,response.getMsg());
	assertNull(response.getNickname());
	}
	
	@Test
	final void testGetUpdateNicknameResponseSuccess() {
	String msg="Se ha cambiado el apodo";
	validator.setValid(true);
	accountMapper.setSuccess(true);
	NicknameResponse response=handler.getUpdateNicknameResponse("nickname", 1);
	assertEquals(msg,response.getMsg());
	assertNotNull(response.getNickname());
	}

	@Test
	final void testGetMsgUpdateMailInvalidMails() {
	String msg="El formato del mail no es valido";
	validator.setValid(false);
	String responseMsg=handler.getMsgUpdateMail("ej@gmail.com","ej@gmail.com","myMail@gmail.com", 1);
	assertEquals(responseMsg,msg);
	}
	
	@Test
	final void testGetMsgUpdateMailMailsNotEquals() {
	String msg="Los mails ingresados no son iguales";
	validator.setValid(true);
	validator.setEqual(false);
	String responseMsg=handler.getMsgUpdateMail("ej@gmail.com","ej@gmail.com","myMail@gmail.com", 1);
	assertEquals(responseMsg,msg);
	}
	
	@Test
	final void testGetMsgUpdateMailNewMailAccountExisted() {
	String msg="Ya existe una cuenta asociada al mail al que se pretende cambiar";
	validator.setValid(true);
	validator.setEqual(true);
	accountMapper.setAccount(new Account());
	String responseMsg=handler.getMsgUpdateMail("ej@gmail.com","ej@gmail.com","myMail@gmail.com", 1);
	assertEquals(responseMsg,msg);
	}
	
	@Test
	final void testGetMsgUpdateMailMyMailIsWrong() {
	String msg="Su mail ingresado no es su mail actual.";
	validator.setValid(true);
	validator.setEqual(true);
	accountMapper.setAccount(null);
	Account account=new Account();
	account.setMail("otherMail@gmail.com");
	accountMapper.setAccount1(account);
	String responseMsg=handler.getMsgUpdateMail("ej@gmail.com","ej@gmail.com","myMail@gmail.com", 1);
	assertEquals(responseMsg,msg);
	}
	
	@Test
	final void testGetMsgUpdateMailFailed() {
	String msg="La direccion de mail no pudo actualizarse.";
	validator.setValid(true);
	validator.setEqual(true);
	accountMapper.setAccount(null);
	Account account=new Account();
	account.setMail("myMail@gmail.com");
	accountMapper.setAccount1(account);
	accountMapper.setSuccess(false);
	String responseMsg=handler.getMsgUpdateMail("ej@gmail.com","ej@gmail.com","myMail@gmail.com", 1);
	assertEquals(responseMsg,msg);
	}
	
	@Test
	final void testGetMsgUpdateMailSuccess() {
	String msg="El mail se actualizo correctamente.";
	validator.setValid(true);
	validator.setEqual(true);
	accountMapper.setAccount(null);
	Account account=new Account();
	account.setMail("myMail@gmail.com");
	accountMapper.setAccount1(account);
	accountMapper.setSuccess(true);
	String responseMsg=handler.getMsgUpdateMail("ej@gmail.com","ej@gmail.com","myMail@gmail.com", 1);
	assertEquals(responseMsg,msg);
	}


	@Test
	final void testGetMsgUpdatePasswordInvalid() {
		String msg="La contraseña ingresada debe tener un formato valido.";
		validator.setValid(false);
		String responseMsg=handler.getMsgUpdatePassword("pass","pass","myPass",1);
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetMsgUpdatePasswordPasswordsEnteredNotEquals() {
		String msg="Las contraseñas deben coincidir.";
		validator.setValid(true);
		validator.setEqual(false);
		String responseMsg=handler.getMsgUpdatePassword("pass","pass1","myPass",1);
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetMsgUpdatePasswordWrongPassword() {
		String msg="Su contraseña es incorrecta.";
		validator.setValid(true);
		validator.setEqual(true);
		Account account=new Account();
		account.setPassword("myPass2");
		accountMapper.setAccount1(account);
		String responseMsg=handler.getMsgUpdatePassword("pass","pass1","myPass",1);
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetMsgUpdatePasswordSuccess() {
		String msg="La contraseña se ha actualizado exitosamente.";
		validator.setValid(true);
		validator.setEqual(true);
		Account account=new Account();
		account.setPassword("myPass");
		accountMapper.setAccount1(account);
		accountMapper.setSuccess(true);
		String responseMsg=handler.getMsgUpdatePassword("pass","pass1","myPass",1);
		assertEquals(msg,responseMsg);
		
	}
	
	@Test
	final void testGetMsgUpdatePasswordFailed() {
		String msg="La contraseña no se ha podido actualizar.";
		validator.setValid(true);
		validator.setEqual(true);
		Account account=new Account();
		account.setPassword("myPass");
		accountMapper.setAccount1(account);
		accountMapper.setSuccess(false);
		String responseMsg=handler.getMsgUpdatePassword("pass","pass1","myPass",1);
		assertEquals(msg,responseMsg);
		
	}

	@Test
	final void testGetSendForgotMailResponseInvalid() {
		String msg="El formato del mail no es valido.";
		validator.setValid(false);
		GenericResponse response=handler.getSendForgotMailResponse("ej@gmail.com");
		assertEquals(msg,response.getMsg());
		assertFalse(response.isSuccess());
	}
	
	@Test
	final void testGetSendForgotMailResponseAccountNotExisted() {
		String msg="No hay una cuenta asociada al mail ingresado.";
		validator.setValid(true);
		accountMapper.setAccount(null);
		GenericResponse response=handler.getSendForgotMailResponse("ej@gmail.com");
		assertEquals(msg,response.getMsg());
		assertFalse(response.isSuccess());
	}
	
	@Test
	final void testGetSendForgotMailResponseAccountExistsMailSended() {
		String msg="Se ha enviado un correo a tu casilla de mail.";
		validator.setValid(true);
		Account account=new Account();
		account.setId(1);
		accountMapper.setAccount(account);
		codeMapper.setSuccess(true);
		GenericResponse response=handler.getSendForgotMailResponse("ej@gmail.com");
		assertEquals(msg,response.getMsg());
		assertTrue(response.isSuccess());
	}


	@Test
	final void testGetUpdatePassResponsePasswordsInvalid() {
		String msg="La contraseña ingresada no tiene un formato valido.";
		validator.setValid(false);
		GenericResponse response=handler.getUpdatePassResponse("pass","repeatedPass",
															"ej@gmail.com", "code");
		assertEquals(msg,response.getMsg());
		assertFalse(response.isSuccess());
	}
	
	@Test
	final void testGetUpdatePassResponsePasswordsNotEquals() {
		String msg="Las contraseñas deben coincidir.";
		validator.setValid(true);
		validator.setEqual(false);
		GenericResponse response=handler.getUpdatePassResponse("pass","repeatedPass",
															"ej@gmail.com", "code");
		assertEquals(msg,response.getMsg());
		assertFalse(response.isSuccess());
	}
	
	@Test
	final void testGetUpdatePassResponseCodesNotEquals() {
		String msg="El mail ingresado o el codigo son incorrectos.";
		validator.setValid(true);
		validator.setEqual(true);
		codeMapper.setCode("code1");
		GenericResponse response=handler.getUpdatePassResponse("pass","repeatedPass",
															"ej@gmail.com", "code");
		assertEquals(msg,response.getMsg());
		assertFalse(response.isSuccess());
	}
	
	@Test
	final void testGetUpdatePassResponsePassUpdatedSuccess() {
		String msg="La contraseña se ha actualizado exitosamente.";
		validator.setValid(true);
		validator.setEqual(true);
		codeMapper.setCode("code");
		codeMapper.setSuccess(true);
		accountMapper.setSuccess(true);
		GenericResponse response=handler.getUpdatePassResponse("pass","repeatedPass",
															"ej@gmail.com", "code");
		assertEquals(msg,response.getMsg());
		assertTrue(response.isSuccess());
	}
	
	@Test
	final void testGetUpdatePassResponsePassUpdatedFailed() {
		String msg="La contraseña no se ha podido actualizar.";
		validator.setValid(true);
		validator.setEqual(true);
		codeMapper.setCode("code");
		codeMapper.setSuccess(true);
		accountMapper.setSuccess(false);
		GenericResponse response=handler.getUpdatePassResponse("pass","repeatedPass",
															"ej@gmail.com", "code");
		assertEquals(msg,response.getMsg());
		assertFalse(response.isSuccess());
	}

	@Test
	final void testDeletedAccountSuccess() {
		accountMapper.setSuccess(true);
		assertTrue(handler.deletedAccount(1));
	}
	
	@Test
	final void testDeletedAccountFailed() {
		accountMapper.setSuccess(false);
		assertFalse(handler.deletedAccount(1));
	}

	@Test
	final void testGetAccountWithMailNotNull() {
		accountMapper.setAccount(new Account());
		assertNotNull(handler.getAccountWithMail("ejemplo@gmail.com"));
	}
	
	@Test
	final void testGetAccountWithMailNull() {
		accountMapper.setAccount(null);
		assertNull(handler.getAccountWithMail("ejemplo@gmail.com"));
	}

	@Test
	final void testGetAccountWithIdNotNull() {
		accountMapper.setAccount1(new Account());
		assertNotNull(handler.getAccountWithId(1));
	}
	
	@Test
	final void testGetAccountWithIdNull() {
		accountMapper.setAccount1(null);
		assertNull(handler.getAccountWithId(1));
	}

	@Test
	final void testGetFriendAccountWithIdNotNull() {
		accountMapper.setAccount(new Account());
		assertNotNull(handler.getFriendAccountWithId(1));
	}
	
	@Test
	final void testGetFriendAccountWithIdNull() {
		accountMapper.setAccount(null);
		assertNull(handler.getFriendAccountWithId(1));
	}

}
