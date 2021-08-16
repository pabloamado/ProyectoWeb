
$(document).ready(function(){
	
	var loginValidator= new LoginValidator();
	loginValidator.checkLogin();
	
	$("#forgotForm").submit(function(e){
		e.preventDefault();
		let validator=new Validator();
		var mail=$("#email").val();
				
	if(validator.mailIsValid(mail)){
		
		var mailBody={
				userMail:mail
			}
		
		sendForgotRequest(JSON.stringify(mailBody));
		
	}else{
		
		return false;
	}
		
		
	});
	
	
});

function sendForgotRequest(mailJson){
	
	 var request = new XMLHttpRequest();
	 request.open('POST','http://localhost:8080/WebProyect/rest/account/sendForgotMail');
	 request.setRequestHeader("Content-Type","application/json");
	 request.onload= () => forgotCallback(request);
    
	 try {
         request.send(mailJson);
         
     } catch (error) {
         alert("Ocurrio el siguiente error :" + error);
     }	
	
}

function forgotCallback(forgotResponse){
	
	if(forgotResponse.status=200){
		
		var reply=JSON.parse(forgotResponse.responseText);
	
		if(reply.success){
			
			alert(reply.msg);
			window.location.href="http://localhost:8080/WebProyect/html/ChangePassword.html";
			
		}
			
		alert(reply.msg);
		
    }else{
    	
        alert("ha ocurrido un error en la peticion con el servidor.");
        
        }  

}