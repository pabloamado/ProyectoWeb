$(document).ready(function(){
	 
	var loginValidator= new LoginValidator();
	loginValidator.checkLogin();
	
	$("#loginForm").submit(function(e){
		e.preventDefault();  
		let validator=new Validator();
		
		var mail=$("#email").val();
		var password=$("#password").val();  
        
		if(validator.mailIsValid(mail) && validator.passwordIsValid(password)){
       
			var login={
					userMail:mail,
					userPassword:password
			}
        
			sendLoginRequest(JSON.stringify(login));
 
       }else{
        
    	   return false;
       }    

	});
	
	
});

function sendLoginRequest(jsonLogin){
   
    var request = new XMLHttpRequest();
    request.open('POST','http://localhost:8080/WebProyect/rest/log/login');
    request.setRequestHeader("Content-Type","application/json");
    request.onload= () => loginCallback(request);
    
    try{
    	request.send(jsonLogin);
    	
    }catch(Error){
    	alert("Ocurrio el siguiente Error: " + error);
    }
    
}

function loginCallback(loginResponse){
    
	if(loginResponse.status=200){
		
		var reply=JSON.parse(loginResponse.responseText);
		
		if(reply.account==null){
			
			 alert(reply.msg);
			 window.location.href="http://localhost:8080/WebProyect";
			 
		} else{
			
				if(reply.success){
				
					window.localStorage.setItem('account',JSON.stringify(reply.account));
					window.location.href="http://localhost:8080/WebProyect/html/Profile.html";
		        
				}else{
				
					alert(reply.msg);
				}
			  		 
		}
			
	} else{
		
		alert("Ha ocurrido un error en la peticion con el servidor.");

	}
	     
}