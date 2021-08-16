$(document).ready(function(){
	
	var loginValidator= new LoginValidator();
	loginValidator.checkNoLogin();
	$("#logOutBtn").click(logout);
	loadFriends();
});

function loadFriends(){
	
	var account=JSON.parse(localStorage.getItem("account"));
	
	idBody={
		accountId:account.id
	}
	
	var request=new XMLHttpRequest();
	request.open('POST', 'http://localhost:8080/WebProyect/rest/request/getFriends');
		    request.setRequestHeader("Content-Type","application/json");
		    request.onload = () => loadFriendsCallBack(request);
		    
	try{
		
		request.send(JSON.stringify(idBody));
	}catch(error){
		 alert("Ocurrio un error al enviar la peticion: "+ error);
	}
	
}

function loadFriendsCallBack(response){
	
	if(response.status==200){
		var friends=JSON.parse(response.responseText);
		
		if(friends!=null){
			
			insertFriendList(friends);
			
		}else{
			
			$("#infoFriends").html("<h1>No tiene ningun amigo agregado</h1>");
		}		
		
	}else{
		
		alert("Ha ocurrido un error en la peticion con el servidor.");
	}
	
}

function insertFriendList(friends){
	
	var friendTags="";
	
	if(friends.length!=0){
		
		var pic;
		
		for(var i=0;i<friends.length;i++){
			
			pic="#";
		
			if(friends[i].profPic!=null){
				pic=friends[i].profPic;
			}
		
			friendTags+=" <li class='list-group-item' style='width:800px;height:100px'> <div id='"+friends[i].requestId+"'>" +
						" <div class='userInfo' >"+friends[i].nicknameUserSender+"</div>"+
						" <img class='userImage' src='"+pic+"'> "+
						" <div id='"+friends[i].idUserSender+"' class='friendsUsersRequestsBtns'>"+
							"<input type='button' class='deleteFriend' value='Eliminar'>" +
							"<input type='button' class='visitFriend' value='Visitar Perfil'>" +
						" </div> </div> </li> ";
		}
	
		//var friendsList=' <li class="list-group-item" style="width:800px;height:100px">'+ friendTags +'</li> ';
	
		$("#infoFriends").append(friendTags);
		$(".deleteFriend").click(deleteFriend);
		$(".visitFriend").click(visitProfile);
		
	}else{
		$("#infoFriends").html("<h1>No tiene ningun amigo agregado</h1>");
	}
}

function deleteFriend(){

	if(window.confirm("Seguro que desea borrar este contacto?")){
	
		idReqBody={
			requestId:$(this).parent().parent().prop("id")
		}
		
		var request=new XMLHttpRequest();
		request.open('POST', 'http://localhost:8080/WebProyect/rest/request/deleteFriend');
		request.setRequestHeader("Content-Type","application/json");
		request.onload = () => {
		    	
		    if(request.status==200){
					
				if(!JSON.parse(request.responseText)){
						
					alert("Hubo un error, intentelo nuevamente.");
				}
					
		    }else{
					
				alert("Ha ocurrido un error en la peticion con el servidor.");
					
			}	
		    	
		    window.location.href="http://localhost:8080/WebProyect/html/Friends.html";	
		    	
		}
		    
		try{
		
			request.send(JSON.stringify(idReqBody));
		
		}catch(error){
			
			alert("Ocurrio un error al enviar la peticion: "+ error);
		}
	
	}
	
}

