package handler;

import java.util.ArrayList;

import entity.Account;
import entity.ExceptionWriter;
import entity.Request;
import entity.RequestState;
import mapper.AccountMapper;
import mapper.RequestMapper;

public class RequestHandler {

	private AccountMapper accountMapper;
	private RequestMapper requestMapper;
	
	public RequestHandler(AccountMapper accountMapper,RequestMapper  requestMapper) {
		this.accountMapper=accountMapper;
		this.requestMapper=requestMapper;
		
	}
	
	//ACCEDE AL MAPPER PARA OBTENER LA LISTA DE USUARIOS
	public ArrayList<Account> getUsers(int accountId) {
		
		ArrayList<Account> users=null;
		
		try {
			users=accountMapper.getUsers(accountId);
			
		}catch(Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}
		
		return users;
	}
	
//ACCEDE AL MAPPER PARA OBTENER LAS SOLICITUDES DE AMISTAD
   public ArrayList<Request> getRequests(int accountId) {
       ArrayList<Request> requestList=null;

       try {
       	 requestList = requestMapper.getRequests(accountId);
       	 
       }catch(Exception e) {
    	   
    	   e.printStackTrace(ExceptionWriter.getStream());
    	   e.printStackTrace();
       }

       return requestList;
   }
	
 //ACCEDE AL MAPPER PARA OBTENER A LOS AMIGOS
   public ArrayList<Request> getFriends(int accountId) {
       
     ArrayList<Request> friendList=null;
       
      try {
    	  
    	  friendList=requestMapper.getFriends(accountId);
    	  
      }catch(Exception e) {
    	  
    	  e.printStackTrace(ExceptionWriter.getStream());
    	  e.printStackTrace();
      }
     
       return friendList;
   }
	
	//ACCEDE AL MAPPER PARA ENVIAR UNA SOLICITUD DE AMISTAD
    public boolean sendRequest(int accountId, int userId) {
    	boolean sended=false;
    	
    	try {
    		
    		 sended = requestMapper.sendRequest(accountId,userId);
    	}catch(Exception e) {
    		
    		e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
    	}

        return sended;
    }

	//ACCEDE AL MAPPER PARA RECHAZAR O ACEPTAR UNA SOLICITUD
    public boolean acceptRejectRequest( int requestId, RequestState requestFlag) {
    	 boolean accepted = false;
    	 
    	 try { 
    		 accepted = requestMapper.sendAcceptRejectRequest(requestId,requestFlag);
    		 
    	 }catch(Exception e) {
    		 
    		e.printStackTrace(ExceptionWriter.getStream());
 			e.printStackTrace();
    	 }
      
        return accepted;
    }
    
//ACCEDE AL MAPPER PARA BORRAR UN AMIGO
    public boolean deleteFriend(int requestId) {
    	 boolean deleted =false;
    	 
    	 try {
    		 deleted = requestMapper.deleteFriend(requestId);
    		 
    	 }catch(Exception e) {
    		 
    		e.printStackTrace(ExceptionWriter.getStream());
 			e.printStackTrace();
    	 }
       
        return deleted;
    }

}
