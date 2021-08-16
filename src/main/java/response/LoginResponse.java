package response;

import entity.Account;

public class LoginResponse extends GenericResponse{

	private Account account;

	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
}
