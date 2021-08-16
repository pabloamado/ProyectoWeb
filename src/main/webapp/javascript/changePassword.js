$(document).ready(function(){
	
let validator=new Validator();
    
	var loginValidator= new LoginValidator();
	loginValidator.checkLogin();
	
    $("#updatedPassForm").submit(function(e){
    	e.preventDefault();
    	var pass=$("#password1").val();
    	var repeatedPass=$("#password2").val();
    	var mail=$("#email").val();
    	var code=$("#code").val();
        if( validator.mailIsValid(mail) && validator.passwordIsValid(pass) && validator.passwordIsValid(repeatedPass) &&
        		validator.equalPassword(pass,repeatedPass)){
       
        	var bodyData={
        		password:pass,
        		repeatedPassword:repeatedPass,
        		email:mail,
        		userCode:code
        	 }
        	
            sendUpdatePasswordRequest(JSON.stringify(bodyData));
            
        }else{
            
            return false;
        }
           
    }); 
	
});

function sendUpdatePasswordRequest(jsonData){
	
	  var request = new XMLHttpRequest();
	    request.open('POST','http://localhost:8080/WebProyect/rest/account/forgotPassword');
	    request.setRequestHeader("Content-Type","application/json");
	    request.onload= () => updatePasswordCallback(request);
	    
	    try{
	    	request.send(jsonData);
	    	
	    }catch(Error){
	    	alert("Ocurrio el siguiente Error: " + error);
	    }
	
}

function updatePasswordCallback(response){
	
	if(response.status==200){
		
		var reply=JSON.parse(response.responseText);
		
		if(reply.success){
			
			alert(reply.msg);
			window.location.href="http://localhost:8080/WebProyect";
		}
			
		alert(reply.msg);
		
		
	}else{
		
		alert("Ha ocurrido un error en la peticion con el servidor.");
	}

}

