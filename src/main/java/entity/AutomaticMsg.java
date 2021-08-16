package entity;

public class AutomaticMsg {

	   public static String subjectPassCode() { 
		   return "AVISO: Codigo para cambio de Contraseña."; 
		   }

	   public static String msgPassCode(String code) {

	        return  "Utilice el siguiente codigo para el cambio de contraseña: " + code ;
	    }
	   
	   public static String subjectPassUpdated() { 
		   return "AVISO: Cambio de Contraseña.";
		   }
	   
	   public static String msgPassUpdated() {
			
			return " Su contraseña ha sido actualizada.";
		}
	   
	   public static String subjectUserRegistered() {
	    	return "AVISO: Registro de cuenta en Web Friends.";
	    }
	   
	   public static  String  msgUserRegistered(String name, String lastName) {
	    	return " Se ha registrado exitosamente " + name + " "+ lastName;
	    }
	   
	   public static String subjectReactivateAccount() { 
		   return "AVISO: Reactivacion de cuenta."; 
		   }
	   
	   public static String msgReactivateAccount(String name, String lastName) {
	    	return " Su cuenta ha sido reactivada " + name + " " + lastName ;
	    }
	   
	   public static String subjectBlockAccount() {
		   return "AVISO: Bloqueo de cuenta";
	   }
	   
	   public static String msgBlockAccount() {
		   return " Su cuenta ha sido bloqueada.";
	   }
	   
	   
	    
	 
		
	    
}

