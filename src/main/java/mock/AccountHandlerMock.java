package mock;

import entity.Account;
import entity.MailSender;
import handler.AccountHandler;
import mapper.AccountMapper;
import mapper.CodeMapper;
import validator.AccountValidator;

public class AccountHandlerMock extends AccountHandler {

	private Account account;
	
	public AccountHandlerMock(AccountMapper accountMapper, CodeMapper codeMapper, 
			AccountValidator validator,MailSender mailSender) {
		super(accountMapper, codeMapper, validator, mailSender);
		
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getAccountWithMail(String mail) {
		
		return account;
	}

}
