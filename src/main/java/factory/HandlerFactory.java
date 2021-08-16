package factory;

import entity.MailSender;
import handler.*;
import validator.*;

public class HandlerFactory {
    
    public static  AccountHandler createAccountHandler(){
    
       return  new AccountHandler(MapperFactory.createAccountMapper(),MapperFactory.createCodeMapper(),
    		   new AccountValidator(),new MailSender());
    }
    
    public  static LoginHandler createLoginHandler(){
       
        return new LoginHandler(MapperFactory.createLoginMapper(),new AccountValidator(),
        			createAccountHandler(), new MailSender());
    }
    
    public static  PostHandler createPostHandler(){
        
        return new PostHandler(MapperFactory.createPostMapper(),new PostValidator());
    }
    
    public static RequestHandler createRequestHandler() {
    	
    	return new RequestHandler(MapperFactory.createAccountMapper(),MapperFactory.createRequestMapper());
    }
     
     
}