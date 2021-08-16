$(document).ready(function(){
	
	var loginValidator= new LoginValidator();
	loginValidator.checkNoLogin();
	$("#logOutBtn").click(logout);
	loadUsers();

});

function loadUsers(){
	
	var account=JSON.parse(localStorage.getItem("account"));
	
	idBody={
		accountId:account.id
	}
	
	var request=new XMLHttpRequest();
	request.open('POST', 'http://localhost:8080/WebProyect/rest/request/getUsers');
		    request.setRequestHeader("Content-Type","application/json");
		    request.onload = () => loadUsersCallBack(request);
		    
	try{
		
		request.send(JSON.stringify(idBody));
	}catch(error){
		 alert("Ocurrio un error al enviar la peticion: "+ error);
	}
}

function loadUsersCallBack(response){
	
	if(response.status==200){
		var users=JSON.parse(response.responseText);
		
		if(users!=null){
			
			insertUsersList(users);
			
		}else{
			
			$("#infoUsers").html("<h1>Por el momento no hay ningun usuario para enviar solicitudes</h1>");
		}
		
		
	}else{
		
		alert("Ha ocurrido un error en la peticion con el servidor.");
	}
			
}

function insertUsersList(users){
	
	var usersTags="";
	
	if(users.length!=0){
		
		var pic;
		
		
		for(var i=0;i<users.length;i++){
			
			pic="#";
			
			if(users[i].profPicture!=null){
				pic=users[i].profPicture;
			}
		
			usersTags+=" <li class='list-group-item' style='width:800px;height:100px'> <div> <div class='userInfo' >"+users[i].nickName+"</div>"+
			"<img class='userImage' src='"+pic+"'> "+
			"<div id='"+users[i].id+"' class='friendsUsersRequestsBtns'>"+
    		"<input type='button' class='sendRequest' value='Enviar solicitud de Amistad'>" +
    		"</div>  </div> </li> ";
		}
	
		//var usersList=' <li class="list-group-item" style="width:800px;height:100px">'+ usersTags +'</li> ';
	
		$("#infoUsers").append(usersTags);
		$(".sendRequest").click(sendFriendRequest);
	
	}else{
		$("#infoUsers").html("<h1>Por el momento no hay ningun usuario para enviar solicitudes</h1>");
	}
}

function sendFriendRequest(){
	
	var account=JSON.parse(localStorage.getItem("account"));

	var userAccId=$(this).parent().prop("id");
	
	idsBody={
			accountId:account.id,
			userId:userAccId
		}
		
		var request=new XMLHttpRequest();
		request.open('POST', 'http://localhost:8080/WebProyect/rest/request/sendRequest');
			    request.setRequestHeader("Content-Type","application/json");
			    request.onload = () => {
			    	
			    	if(request.status==200){
						
						if(!JSON.parse(request.responseText)){
							
							alert("La solicitud no pudo ser enviada.");	
						}
								
					}else{
						alert("Ha ocurrido un error en la peticion con el servidor.");
						
					}
			    	
			    	window.location.href="http://localhost:8080/WebProyect/html/Users.html";
			    	
			    }
			    
		try{
			
			request.send(JSON.stringify(idsBody));
			
		}catch(error){
			 alert("Ocurrio un error al enviar la peticion: "+ error);
		}
	
	
}

