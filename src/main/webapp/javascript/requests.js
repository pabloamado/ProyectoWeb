
$(document).ready(function(){
	
	var loginValidator= new LoginValidator();
	loginValidator.checkNoLogin();
	$("#logOutBtn").click(logout);
	loadRequests();
});

function loadRequests(){
	
	var account=JSON.parse(localStorage.getItem("account"));
	
	idBody={
		accountId:account.id
	}
	
	var request=new XMLHttpRequest();
	request.open('POST', 'http://localhost:8080/WebProyect/rest/request/getRequests');
		    request.setRequestHeader("Content-Type","application/json");
		    request.onload = () => loadRequestsCallBack(request);
		    
	try{
		
		request.send(JSON.stringify(idBody));
	}catch(error){
		 alert("Ocurrio un error al enviar la peticion: "+ error);
	}
	
}

function loadRequestsCallBack(response){
	
	if(response.status==200){
		var requests=JSON.parse(response.responseText);
		
		if(requests!=null){
			
			insertRequestList(requests);
			
		}else{
			
			$("#infoRequests").html("<h1>No tiene solicitudes pendientes</h1>");
		}		
		
	}else{
		
		alert("Ha ocurrido un error en la peticion con el servidor.");
	}
	
}

function insertRequestList(requests){
	
	var requestTags="";
	
	if(requests.length!=0){
		
		var pic;
		
		for(var i=0;i<requests.length;i++){
			
			pic="#";
			
			if(requests[i].profPic!=null){
				pic=requests[i].profPic;
			}
		
			requestTags+=" <li class='list-group-item' style='width:800px;height:100px'> <div id='"+requests[i].requestId+"'>" +
						" <div class='userInfo' >"+requests[i].nicknameUserSender+"</div>"+
						" <img class='userImage' src='"+pic+"'> "+
						"<div id='"+requests[i].idUserSender+"' class='friendsUsersRequestsBtns'>"+
							"<input type='button' class='acceptRequest' value='Aceptar'>" +
							"<input type='button' class='rejectRequest' value='Rechazar'>" +
						"</div> </div> </li> ";
		}
	
		//var requestList=' <li class="list-group-item" style="width:800px;height:100px">'+ requestTags +'</li> ';
	
		$("#infoRequests").append(requestTags);
		$(".acceptRequest").click(acceptRequest);
		$(".rejectRequest").click(rejectRequest);
		
	}else{
		$("#infoRequests").html("<h1>No tiene solicitudes pendientes</h1>");
	}
}

function acceptRequest(){
	
	idBody={	
			requestId:$(this).parent().parent().prop("id")
		}
	
	sendRequest("http://localhost:8080/WebProyect/rest/request/acceptRequest", idBody);	
		
}

function rejectRequest(){

	idBody={	
			requestId:$(this).parent().parent().prop("id")
		}

	sendRequest("http://localhost:8080/WebProyect/rest/request/rejectRequest",idBody);
		
}

function sendRequest(path, idBody){
	
	var request=new XMLHttpRequest();
	request.open('POST',path);
	request.setRequestHeader("Content-Type","application/json");
	request.onload = () => {
		    	
		if(request.status==200){
					
			if(!JSON.parse(request.responseText)){
						
				alert("Hubo un error, intentelo nuevamente.");
						
			}
					
		}else{
			
			alert("Ha ocurrido un error en la peticion con el servidor.");
		
		}
		
		window.location.href="http://localhost:8080/WebProyect/html/FriendshipRequest.html";
		    	
	}
		    
	try{
		
		request.send(JSON.stringify(idBody));
		
	}catch(error){
		
		 alert("Ocurrio un error al enviar la peticion: "+ error);
	}

}

