package mock;

import dao.Dao;
import entity.Account;
import mapper.LoginMapper;

public class LoginMapperMock  extends LoginMapper{

	private boolean success;
	
	public LoginMapperMock(Dao dao) {
		super(dao);
		
	}
	
    public boolean updateAccountAttempts(Account account) {
    	
        return success;
    }   
  
    public boolean blockAccount(Account account) {

        return success;
    }
    
    public void setSuccess(boolean success){
    	this.success=success;
    }
}
