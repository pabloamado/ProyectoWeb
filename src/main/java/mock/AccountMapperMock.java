
package mock;

import java.util.ArrayList;
import dao.Dao;
import entity.Account;
import helper.Helper;
import mapper.AccountMapper;

public class AccountMapperMock extends AccountMapper {
   
    private boolean success;
    private Account account;
    private Account account1;
    private ArrayList<Account> users;

	public AccountMapperMock(Dao userDao,Helper helper) {
        super(userDao, helper);
    }
    
	public void setSuccess(boolean success){
	        this.success=success;
	}
	 
    public void setAccount(Account account) {
		this.account = account;
	}
    
    public void setAccount1(Account account1) {
		this.account1 = account1;
	}

	public void setUsers(ArrayList<Account> users) {
		this.users = users;
	}

    public boolean saveUser(Account user){
    	
        return success;
    }
      
 	public boolean saveAccount(Account account) {
 	
 		return success;
 	}

 	public boolean updateAccount(Account account) {
 
 		return success;
 	}

 	public boolean deleteAccount(int accountId) {

 		return success;
 	}

 	public boolean updateNickname(String nickname, int accountId) {

 		return success;
 	}

 	public boolean updateMail(String newMail, int accountId) {
 
 		return success;
 	}

 	public boolean updatePassword(String newPass, String mail, int flag) {

 		return success;
 	}

 	public Account getAccountWithMail(String mail) {

 		return account;
 	}
 	
 	public Account getAccountWithId(int accountId) {

 		return account1;
 	}

 	public ArrayList<Account> getUsers(int accountId) {
 	
 		return users;
 	}

 	public Account getFriendAccountWithId(int friendId) {
 		
 		return account;
 	}

}
