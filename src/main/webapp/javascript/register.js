
$(document).ready(function(){

	var loginValidator= new LoginValidator();
	loginValidator.checkLogin();
	
	$("#registerForm").submit(function(e){
	    e.preventDefault();
	    
	  	    
	   var firstResponse=grecaptcha.getResponse();
	    
	    if(firstResponse!=""){
	    	
	   //NO ANDA PORQUE FALTA EL Access-Control-Allow-Origin EN LOS ENCABEZADOS DEL SERVIDOR
	   /* var myKey='6LeU4C8bAAAAAH0SteZGdDMZVlj31bj_UkzsI140';
	    var request=new XMLHttpRequest();
	    request.open("POST","https://www.google.com/recaptcha/api/siteverify?secret=6LeU4C8bAAAAAH0SteZGdDMZVlj31bj_UkzsI140&response="+firstResponse); 
	    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");    
        request.onload = () => {
        var response=JSON.parse(request.responseText);
        
        if(response.success){// si es verdadero es porque el captcha se resolvio exitosamente
        	// hacer validaciones del formulario y enviarlo al back
        }
        
        };
               
        request.send();*/
      
        let validator=new Validator();
		var name=$("#name").val();
		var lastName=$("#lastName").val();  
		var birthday = $("#bornDate").val();  
		var mail1 = $("#mail").val();  
		var mail2 = $("#repeatedMail").val();  
		var password1 = $("#password").val();  
		var password2 = $("#repeatedPassword").val();  
		var nickname = $("#nickname").val();  
	
		var isValid=validator.validateRegister(name,lastName,birthday,mail1,mail2,password1,password2,nickname);  
	   
		if(isValid){
		
			var registerData={
	        		userName:name,
					userLastName:lastName,
					userBirthday:birthday,
					userMail:mail1,
					userPassword:password1,
					userNickname:nickname
	        	}
	        
            sendRegisterRequest(JSON.stringify(registerData));
		
		}else{
					 
			return false;
		}
	      
	  }else{
		  alert("Tienes que completar elcaptcha");
		  
	  }
	});
	
	
});


function sendRegisterRequest(jsonAccount) {

        var request = new XMLHttpRequest();
        request.open('POST','http://localhost:8080/WebProyect/rest/account/register');
        request.setRequestHeader("Content-Type","application/json");
        request.onload = () => registerCallback(request);
            
        try {
            request.send(jsonAccount);
        } catch (error) {
            alert("Ocurrio el siguiente error: " + error);
        }

    

}

function registerCallback(registerResponse) {
 

	if(registerResponse.status==200){
		
        var reply = JSON.parse(registerResponse.responseText);
        
       alert(reply);
               
	}else{
		alert("Ha ocurrido un error en la peticion con el servidor.");
		
	}
	
	window.location.href="http://localhost:8080/WebProyect";
}
    

