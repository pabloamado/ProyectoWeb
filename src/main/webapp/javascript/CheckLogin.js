
class LoginValidator{
	
	checkLogin(){
		
		var account=JSON.parse(localStorage.getItem('account'));
		if(account!=null){
			
			window.location.href="http://localhost:8080/WebProyect/html/Profile.html";	
				
		}
		
	}

	 checkNoLogin(){
		 
		var account=JSON.parse(localStorage.getItem('account'));
		
		if(account==null){
			
			window.location.href="http://localhost:8080/WebProyect";	

		}

}

}
