
$(document).ready(function(){
    
	
	var loginValidator= new LoginValidator();
	loginValidator.checkNoLogin();
	$("#logOutBtn").click(logout);
	$(".configForm").hide();
	$(".configBtn").click(showForm);
    
    $("#submitNickname").click(submitNickname);
    $("#submitMail").click(submitMail);
    $("#submitPassword").click(submitPassword);
	
	$("#closeBtn").click(closeAccount);
     
});

function showForm(){
    
	$(this).next(".configForm").toggle(500);
	
}

function submitNickname(){
    
    let validator=new Validator();
    
    $("#nicknameForm").submit(function(e){
    	
    	e.preventDefault();
        	
        if( validator.nicknameIsValid($("#nickname").val()) ){
       
        	var account=JSON.parse(localStorage.getItem('account'));
        	
        	var nick={
        		id:account.id,
        	    nickname:$("#nickname").val()
        	 }
        	
        	 $("#nickname").val("");
            sendChangeNicknameRequest(JSON.stringify(nick));
            
        }else{

            return false;
        }
           
    }); 
    
}

function submitMail(){
    
    let validator=new Validator();
    
     $("#mailForm").submit(function(e){
    	 
    	 e.preventDefault();
    	 var mail1=$("#mail1").val();
    	 var mail2=$("#mail2").val();
    	 var myMail=$("#mail3").val();
    	
    	 var account=JSON.parse(localStorage.getItem("account"));
    	 
        if(validator.mailIsValid(mail1) && validator.mailIsValid(mail2) && validator.mailIsValid(myMail) &&
        		validator.equalMail(mail1,mail2) && validator.equalMail(myMail,account.mail) && validator.notEqualMail(myMail,mail1)){
                  	 
            var mailsBody={
            	accountId:account.id,
            	actualMail:myMail,
                mail:mail1,
                repeatedMail:mail2
            }
            
            $("#mail1").val('');
            $("#mail2").val('');
            $("#mail3").val('');
            sendChangeMailRequest(JSON.stringify(mailsBody));
            
        }else{
          
            return false;
        }
                   
    });
    
}

function submitPassword(){
    
    let validator=new Validator();
    
     $("#passwordForm").submit(function(e){
    	 
    	 e.preventDefault(e);
    	
    	 
    	 var newPass=$("#password1").val();
    	 var repeatedNewPass=$("#password2").val();
    	 var myPass=$("#actualPass").val();
    	 
         if(validator.passwordIsValid(newPass) && validator.passwordIsValid(repeatedNewPass) && validator.passwordIsValid(myPass) &&
        	validator.equalPassword(newPass,repeatedNewPass) && validator.notEqualPassword(myPass,newPass)) {
        	 
        	var account=JSON.parse(localStorage.getItem('account'));
        	
            var passwords={
            	accountId:account.id,
            	actualPassword:myPass,
                newPassword:newPass,
                repeatedNewPassword:repeatedNewPass
                
             }
                
            $("#password1").val('');
            $("#password2").val('');
            $("#actualPass").val('');
            
        sendChangePasswordRequest(JSON.stringify(passwords));    
            
        }else{
            
            return false;
        }
        
    });
}


function closeAccount(){
    
	if(window.confirm("Esta seguro que desea cerrar su cuenta?")){
		
		try{
		
			var account=JSON.parse(localStorage.getItem('account'));
			
			idBody={
				id:account.id
			}
			
			sendDeleteRequest(JSON.stringify(idBody));
			
		} catch(error){
			alert("Ocurrio un error al enviar la peticion: "+ error);
			
		}
		
	}
}

function sendChangeNicknameRequest(jsonNickname){
    
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/WebProyect/rest/configuration/updateNickname');
    request.setRequestHeader("Content-Type","application/json");
    request.onload = () => updateNickCallback(request);
            
   
    try {
    	
        request.send(jsonNickname);
        
    } catch (error) {
    	
         alert("Ocurrio un error al enviar la peticion: "+ error);
    }
    
}

function updateNickCallback(response){
    
    if(response.status=200){
		var account=JSON.parse(localStorage.getItem("account"));
        var reply = JSON.parse(response.responseText);
        
        if(reply.nickname!=null){
        	account.nickName=reply.nickname;
        	localStorage.setItem("account",JSON.stringify(account));
        	$("#nickname").val("");
        }
        
        	alert(reply.msg);
        	   
	}else{
		
		alert("Ha ocurrido un error inesperado con el servidor.");
		
	}
}

function sendChangeMailRequest(jsonMails){
   
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/WebProyect/rest/configuration/updateMail');
    request.setRequestHeader("Content-Type","application/json");
    request.onload = () => changeCallback(request);
            
    try {
        request.send(jsonMails);
        
    } catch (error) {
    	
         alert("Ocurrio un error al enviar la peticion: " + error);
    }
    
}


function sendChangePasswordRequest(jsonPasswords){
    
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/WebProyect/rest/configuration/updatePassword');
    request.setRequestHeader("Content-Type","application/json");
    request.onload = () => changeCallback(request);
            
    try {
    	
        request.send(jsonPasswords);
        
    } catch (error) {
         alert("Ocurrio un error al enviar la peticion: "+ error);
    }
    
}

function sendDeleteRequest(jsonId){
	
	var request=new XMLHttpRequest();
	request.open('POST', 'http://localhost:8080/WebProyect/rest/configuration/deleteAccount');
	request.setRequestHeader("Content-Type","application/json");
	request.onload = () => deleteCallback(request);
	
	try{
		
		request.send(jsonId);
		
	}catch(error){
		alert("Ocurrio un error al enviar la peticion: "+ error);
		
	}
	
}

function changeCallback(response){
    
    if(response.status=200){
		
        var reply = JSON.parse(response.responseText);
        
        alert(reply);
        
	}else{
		
		alert("Ha ocurrido un error inesperado con el servidor.");
		
	}
}

function deleteCallback(response){

	 if(response.status==200){
			
		 var deleted = JSON.parse(response.responseText);
	        
	     if (deleted) {
	        	
	        alert("La cuenta ha sido borrada.");
	        
	        localStorage.clear();
	        
	        window.location.href="http://localhost:8080/WebProyect";
	               
	     } 
	      	
	     alert("Hubo un error al intentar borrar la cuenta.");
	        	        
	}else{
		
			alert("Ha ocurrido un error inesperado con el servidor.");	
	}
	 
}
