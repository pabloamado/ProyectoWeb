package test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import entity.Account;
import validator.AccountValidator;

public class AccountValidatorTest {

	private AccountValidator validator = new AccountValidator();
	
	@Test
	public void testAccountIsValid() {
		Account user = new Account();
		user.setId(2);
		user.setAttempts(0);
		user.setDeleted(false);
		user.setBlocked(false);
		user.setBirthday(LocalDate.parse("1994-05-03"));
		user.setLastName("martinez");
		user.setName("julieta");
		user.setMail("ejemplo12@hotmail.com");
		user.setPassword("asdfgh123");
		user.setNickName("Juliet");

		boolean result=validator.accountIsValid(user);
		assertTrue(result);

	}

	@Test
	public void testAccountIsInvalid() {
		Account user = new Account();
		
		boolean result = validator.accountIsValid(user);
		assertFalse(result);
	}

	@Test
	public void testAccountIsNull() {
		Account user = null;
	
		boolean result = validator.accountIsValid(user);
		assertFalse(result);
	}

	@Test
	public void testNameIsInvalid() {
		String name = "·$&/()asda/(|";
		
		boolean result = validator.nameIsValid(name);
		assertFalse(result);

	}

	@Test
	public void testNameIsValid() {
		String name = "Joaquin alberto fede";
		boolean result = validator.nameIsValid(name);
		assertTrue(result);

	}

	@Test
	public void testNameIsNull() {
		String name = null;
	
		boolean result = validator.nameIsValid(name);
		assertFalse(result);

	}

	@Test
	public void testNicknameIsInvalidLength() {
		String nick = "asd";
	
		boolean result = validator.nickNameIsValid(nick);
		assertFalse(result);

	}

	@Test
	public void testNicknameIsValidLength() {
		String nick = "Joaquin alberto fede";

		boolean result = validator.nickNameIsValid(nick);
		assertTrue(result);

	}

	@Test
	public void testNicknameIsNull() {
		String nick = null;
		
		boolean result = validator.nickNameIsValid(nick);
		assertFalse(result);

	}

	@Test
	public void testMailIsValid() {
		String email = "pablo-476@hotmail.com";
	
		boolean result = validator.mailIsValid(email);
		assertTrue(result);

	}

	@Test
	public void testMailIsInvalid() {

		String email = "este mail no es un mail";

		boolean result = validator.mailIsValid(email);
		assertFalse(result);

	}

	@Test
	public void testMailIsNull() {

		String email = null;
		
		boolean result = validator.mailIsValid(email);
		assertFalse(result);

	}

	@Test
	public void testPasswordIsValid() {

		String password = "DebeSer1d2346";
		
		boolean result = validator.passwordIsValid(password);
		assertTrue(result);

	}

	@Test
	public void testPasswordIsInvalid() {
		String password = "@@@@@@++++++****";
		
		boolean result = validator.passwordIsValid(password);
		assertFalse(result);

	}

	@Test
	public void testPasswordIsNull() {
		String password = null;
	
		boolean result = validator.passwordIsValid(password);
		assertFalse(result);

	}

	@Test
	public void testHasLegalAgeValid() {
		LocalDate date = LocalDate.of(1994, 5, 27);

		boolean result = validator.hasLegalAge(date);
		assertTrue(result);
	}

	@Test
	public void testHasLegalAgeInvalid() {
		LocalDate date = LocalDate.of(2015, 3, 5);

		boolean result = validator.hasLegalAge(date);
		assertFalse(result);

	}

	@Test
	public void testHasLegalAgeNull() {
		LocalDate date = null;
	
		boolean result = validator.hasLegalAge(date);
		assertFalse(result);

	}

}