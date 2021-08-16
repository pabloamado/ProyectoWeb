//FUNCION DISPARA DESDE FRIENDS.HTML
function visitProfile(){

	idBody={
	friendId:$(this).parent().prop("id")
	}
	
	var request=new XMLHttpRequest();
	request.open('POST','http://localhost:8080/WebProyect/rest/account/getFriendAccount');
	request.setRequestHeader("Content-Type","application/json");
	request.onload = () => visitProfileCallBack(request);
		    
	try{
		
		request.send(JSON.stringify(idBody));
		
	}catch(error){
		 alert("Ocurrio un error al enviar la peticion: "+ error);
	}
}

//FUNCION QUE OBTIENE LA CUENTA DEL AMIGO
function visitProfileCallBack(response){
	
	if(response.status==200){
		var friend=JSON.parse(response.responseText);
		
		if(friend!=null){
			
			window.localStorage.setItem("friend", JSON.stringify(friend));
			window.location.href="http://localhost:8080/WebProyect/html/FriendProfile.html";
			
		}else{
			
			alert("Ocurrio un error mientras se intentaba cargar los datos,vuelva a intentarlo.");
		}		
		
	}else{
		
		alert("Ha ocurrido un error en la peticion con el servidor.");
	}
		
}

//FUNCION QUE SE DISPARA AL CARGAR FRIENDPROFILE.HTML

function loadFriendProfile(){
	
	var loginValidator= new LoginValidator();
	loginValidator.checkNoLogin();
	
	$("#logOutBtn").click(logout);
	
	var friend=JSON.parse(localStorage.getItem('friend'));
	
	var account=JSON.parse(localStorage.getItem('account'));
	
	$("#userNick").html(friend.nickName);//cargamos el nick del amigo
	
	$("#imgProfile").prop("src",friend.profPicture);//cargamos foto del amigo
	
	
	if(friend.postList!=null){
				
		for(var i=0;i<friend.postList.length;i++){
			
			var commentsTags="";
			
			if(friend.postList[i].comments!=null){
				
				for(var j=0; j<friend.postList[i].comments.length ;j++){
					
					if(friend.postList[i].comments[j]!=null){
						
						commentsTags+=' <div class="postComment" id="'+friend.postList[i].comments[j].commentId+'">'+
						'<div class="commentHeader">'+
							'<image class="userPicComment"src="'+friend.postList[i].comments[j].profPic+'"></image>'+
							'<div class="userNameComment">'+friend.postList[i].comments[j].nickName+'</div><br>' + 
							'<div class="dateComment">'+friend.postList[i].comments[j].commentDate.year+"-"+
														friend.postList[i].comments[j].commentDate.month+"-"+
														friend.postList[i].comments[j].commentDate.day+'</div>'+
							'</div>'+
							'<div class="commentBody">'+		
								'<div class="commentContent">'+
									'<p>'+friend.postList[i].comments[j].content+'</p>'+
								'</div> '+	   		   
							'</div> '; 	
					
					if(friend.postList[i].comments[j].comOwnerId==account.id){
						 commentsTags+='<div class="eraseCommentBtn">'+		
											'<input type="button" class="deleteComment" value="Eliminar Comentario">'+    
										'</div> ';
					}	

					commentsTags+='</div> ';
				
					}
				}
				
			}
			
			var imgTagStyle="";
			
			if(friend.postList[i].postImg!=""){
				
				imgTagStyle=" <section style='width:200px'>" +
								" <img src='"+friend.postList[i].postImg +"'  width='200' height='175'>" +
							"</section> ";
				}
			
			$("#allPosts").prepend("<br>" +
			"<div class='myPost' id='"+friend.postList[i].postId+"'> " +
         	 	//"<div class='erasePostBtn'> " +
         	 	//	"<input type='button' class='deletePostBtn' value='Eliminar Publicación'>" +
         		//"</div>"+
         		
         		"<div class='postHeader'>"+
         			"<image class='userPicPost' src='"+ friend.profPicture +"'></image>"+
         			"<div class='userNamePost'>"+friend.nickName+"</div><br>"+
         			"<div class='datePost'>"+friend.postList[i].postDate.year+"-"+friend.postList[i].postDate.month+"-"+
         			friend.postList[i].postDate.day+"</div>"+
         		"</div>"+
         		
         		"<div class='postBody'>"+
         			"<div class='contentPost'><p>"+friend.postList[i].postContent+"</p></div>"+ imgTagStyle +
         		"</div>" + commentsTags +
         		
         		//ACA SE INYECTAN LOS COMENTARIOS
         		"<div class='makeCommentSection'>"+
         			"<div>"+
         				"<textarea placeholder='Has un comentario' class='areaComment'></textarea>" +
         				"<div class='divCommentBtn'>"+
         					"<input type='button' class='doComment'  value='Comentar'>"+
         				"</div>"+
         			"</div>"+
         			
         		"</div>" +
         	"</div><br>");
			
		}
		
		$(".deleteComment").click(deleteComment);
		$(".doComment").click(commentPost);
	}
	
	
}


//esto es igual
function commentPost(){
	
	 var textArea=$(this).parent().prev("textarea");
	 var content= textArea.val();
	 var idPost=$(this).parent().parent().parent().parent().prop("id");
	 
	 $(this).attr("id","commentId");
	 
	 if(content=='' || content.trim().length < 1){
   	
       alert("Debes escribir algo primero antes de comentar.");
       
   	}else if(content.length>240){
   	
   		alert("El comentario no puede ser superior a los 240 caracteres.");
   		
   	}else{
   		
   		var account=JSON.parse(localStorage.getItem('account'));
   		
   		var date=new Date(); 
        var year=date.getUTCFullYear();
        var month=date.getUTCMonth()+1;
        var day=date.getUTCDate();
           
        if(month<10){
          month="0"+month;
        }
        if(day<10){
          day="0"+day;
        }
           
   		var commentBody={
   			accountId:account.id,
   			commentDate:year+"-"+month+"-"+day,
   			commentContent:content,
   			postId:idPost
   		}
   		
   		var request= new XMLHttpRequest();
   	 
   		request.open('POST','http://localhost:8080/WebProyect/rest/post/makeComment');
   		request.setRequestHeader("Content-Type","application/json");
   		request.onload= () => {
   			
   			if(request.status==200){
   				
   				var reply=JSON.parse(request.responseText);
   				
   				if(reply.success){
   					
   					insertComment(reply.comment);
   					
   				}else{
   					alert(reply.msg);
   				}
   				
   			}else{
   				
   				 alert("Ha ocurrido un error en la peticion con el servidor.");
   			}
   			
   		};
   		    
   		 try{
   		    request.send(JSON.stringify(commentBody));
   		    	
   		 }catch(Error){
   		    alert("Ocurrio el siguiente Error: " + error);
   		 }
  		
   	}
	 
	 textArea.val("");
	 
	}


//MODIFICAR ESTO
function insertComment(comment){
	
	var account=JSON.parse(localStorage.getItem('account'));
	
	$('#commentId').parent().parent().parent().before('<div class="postComment" id="'+comment.commentId+'">'+
				'<div class="commentHeader">'+
					'<image class="userPicComment" src="'+account.profPicture+'"></image>'+
					'<div class="userNameComment">'+account.nickName+'</div><br>' + 
					'<div class="dateComment">' +comment.commentDate.year+"-"+comment.commentDate.month+"-"+comment.commentDate.day+'</div>'+
    
				'</div>'+

				'<div class="commentBody">'+
	
					'<div class="commentContent">'+
						'<p>'+comment.content+'</p>'+
					'</div> '+
   		   
				'</div>'+  
 
				'<div class="eraseCommentBtn">'+
	
					'<input type="button" class="deleteComment" value="Eliminar Comentario">'+
    
				'</div>'+

			'</div>');
    		
		$('#commentId').removeAttr('id');	
    	$(".deleteComment").click(deleteComment);
}


function deleteComment(){
	//MODIFICAR ESTO ME PARECE
	if(window.confirm("Realmente desea borrar el comentario?")){
		
		var idComment=$(this).parent().parent().prop("id");
		
		$(this).parent().parent().attr("id","deleteComment");
			
		var idComment={
				idCom:idComment
		}
		
		var request= new XMLHttpRequest();
		request.open('POST','http://localhost:8080/WebProyect/rest/post/deleteComment');
		request.setRequestHeader("Content-Type","application/json");
		request.onload= () => {
			
			if(request.status==200){
				
				if(JSON.parse(request.responseText)){
					$("#deleteComment").remove();
					
				}else{
					
					alert("Ocurrio un error y no se pudo borrar el comentario.");
				}
				
			}else{
				
				alert("Ha ocurrido un error en la peticion con el servidor.");
			}
			
		}
		    
		 try{
		    request.send(JSON.stringify(idComment));
		    	
		 }catch(Error){
		    alert("Ocurrio el siguiente Error: " + error);
		 }
			
	}	

}

