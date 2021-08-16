package mapper;

import java.util.HashMap;
import dao.Dao;
import entity.Account;
import entity.ExceptionWriter;

public class LoginMapper {
    
    private Dao dao;
    
    public LoginMapper(Dao dao){
        this.dao=dao;
    }
        
    //prepara el mapper para actualizar los intentos para loguear
    public boolean updateAccountAttempts(Account account) {
    	boolean success=false;
        HashMap<Integer, Object> userMap = new HashMap<>();
        
        userMap.put(1, account.getAttempts());
        userMap.put(2,account.getId());
        
        String query="update social.account set acc_attempts=? where acc_id=?";
        
        try {
        	
        	 success= dao.doOperation(query,userMap);
        	 
        }catch(Exception e) {
        	
        	e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
        }
      
        return success;
    }   

    //prepara el mapper para bloquear la cuenta
    public boolean blockAccount(Account account) {
    	boolean success= false;
        HashMap<Integer, Object> userMap = new HashMap<>();
        
        userMap.put(1,account.getAttempts());
        userMap.put(2,account.getId());
        
        String query="update social.account set acc_blocked=true,acc_attempts=? where acc_id=?";
        
        try {
        	
        	success = dao.doOperation(query,userMap);      
        	
        }catch(Exception e) {
        	
        	e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
        }
       
        return success;
    }
  
}
