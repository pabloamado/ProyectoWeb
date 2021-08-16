package validator;

import java.time.LocalDate;
import java.time.Period;
import entity.Account;

public class AccountValidator {

	private static final int MIN_LENGHT_NAME = 4;
	private static final int MIN_LENGHT_PASSWORD = 6;
	private static final int MAX_LENGHT = 20;
	private static final int LEGAL_AGE = 18;

	public boolean accountIsValid(Account account) {
		boolean valid =false;
		
		if(account!=null) {	
		valid=true;
		valid &= nameIsValid(account.getName());
		valid &= nameIsValid(account.getLastName());
		valid &= hasLegalAge(account.getBirthday());
		valid &= passwordIsValid(account.getPassword());
		valid &= mailIsValid(account.getMail());
		valid &= nickNameIsValid(account.getNickName());
		}
		return valid;
	}

	public boolean nameIsValid(String name) {
		
		boolean nameValid = false;
		if(name!=null) {
		nameValid=true;
		nameValid &= name.length() >= MIN_LENGHT_NAME && name.length() <= MAX_LENGHT;
		nameValid &= !name.matches("[' ']*") && name.matches("[a-zA-Z' ']*");
		}
		return nameValid;
	}

	public boolean nickNameIsValid(String nickName) {

		boolean nicknameIsValid = false;
		if(nickName != null) {
			nicknameIsValid=true;
			nicknameIsValid &= nickName.length() >= MIN_LENGHT_NAME && nickName.length() <= MAX_LENGHT;
			//HAY UN PROBLEMA CON EL PATTERN USADO EN EL JAVASCRIPT  SI LO IMPLEMENTO ACA
		}

		return nicknameIsValid; 
	}

	public boolean mailIsValid(String email) {
		
		boolean emailValid = false;
		if(email!=null) {
			emailValid=true;
			emailValid &= email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[["
					+ "0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
		}

		return emailValid;
	}

	public boolean passwordIsValid(String password) {
		
			boolean passwordValid = false;
			if(password != null) {
				passwordValid=true;	
				passwordValid &= password.length() >= MIN_LENGHT_PASSWORD && password.length() <= MAX_LENGHT;
				passwordValid &= password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$"); 
						
			}

		return passwordValid;
	}

	public boolean hasLegalAge(LocalDate birthday) {
		boolean isValid=false;
		if(birthday!=null) {
			isValid=true;
			
			LocalDate actualDate = LocalDate.now();
			boolean dateBefore = birthday.isBefore(actualDate);
			
			if(dateBefore) {
				
				Period period = Period.between(birthday, actualDate);
				isValid = period.getYears() >= LEGAL_AGE;
			}
		}
		
		return isValid;
	}

	public boolean equals(String string1, String string2) {

		return string1.equals(string2);
	}

}
