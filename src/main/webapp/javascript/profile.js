
$(document).ready(function(){
	
	
	var loginValidator= new LoginValidator();
	loginValidator.checkNoLogin();
	$("#logOutBtn").click(logout);
	
	loadContent();
	$("#myPostInputFile").change(transferToDivField);
    $("#myPostBtn").click(makePost);
    $(".deletePostBtn").click(deletePost);
	$(".deleteComment").click(deleteComment);
	$(".doComment").click(commentPost);
	
	 	
});
//FUNCION PARA CARGAR TODO LO DEL PERFIL
function loadContent(){
	
	var account=JSON.parse(localStorage.getItem('account'));
	
	$("#userNick").html(account.nickName);
	
	$("#imgProfile").prop("src",account.profPicture);
	
	getPosts();
	
}


//FUNCIONA: OBTIENE LA IMAGEN, LA TRANSFORMA Y LA TRANSFIERE 
function transferToDivField(e){
	
		var file=e.target.files;
		
		var myFile=file[0];
		
		if(!myFile.type.match(/image/) ){
	        
	        alert("Solo se pueden publicar imagenes.");
	        
	    } else if(myFile.size>3000000){
		
	    	 alert("Seleccione un archivo que sea igual o inferior a 3 MB.");
	    	 
	    }else{
			var reader=new FileReader();
		
			reader.readAsDataURL(myFile); 
				
			reader.addEventListener("load",transfer,false);
		}
	
}


//SE ENCARGA DE PASAR EL URL DE IMAGEN A LA SECCION PARA QUE SE MUESTRE LA IMAGEN PREVIA
function transfer(e){
	
	var imgPath=e.target.result;// EL LECTOR DEVUELVE LA IMAGEN EN BASE64

	$("#areaFile").css({"height":"175px","width":"200px","margin-left":"100px", "display":"block"});
	$("#postImg").css({"width":"100%","height":"100%", "display":"block"});
	$("#postImg").prop("src",imgPath);
}

//FUNCIONO EL PROTOTIPO:FUNCION PARA HACER PUBLICACIONES 
function makePost(){
	    
	var content=$("#postContent").val();
	
    if(content=='' || content.trim().length < 1){
    	
        alert("Debes escribir algo primero antes de publicar.");
        
    	}else if(content.length>240){
    	
    		alert("La publicacion no puede ser superior a los 240 caracteres.");
    		
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
            
    		var post={
      	        accountId:account.id,
      	        postDate:year+"-"+month+"-"+day,
      	        postContent:content,
      	        postImg:""
      	        }  
    
    		var src=$("#postImg").prop("src");
    		
    		if(src!="http://localhost:8080/WebProyect/html/Profile.html#"){
    			
    			post.postImg=$("#areaFile img").prop('src');
    		}
    		
    		resetPostArea();
    		
    		sendPostRequest(JSON.stringify(post))
    	}	      
    }
   

function sendPostRequest(jsonPost){
	
	 var request = new XMLHttpRequest();
	 
	 request.open('POST','http://localhost:8080/WebProyect/rest/post/makePost');
	 request.setRequestHeader("Content-Type","application/json");
	 request.onload= () => postCallback(request);
	    
	 try{
	    request.send(jsonPost);
	    	
	 }catch(Error){
	    alert("Ocurrio el siguiente Error: " + error);
	 }
	 
}

function postCallback(response){
	
	if(response.status==200){
		
		var reply=JSON.parse(response.responseText);
		
		if(reply.success){// ACA MODIFIQUE
			
			insertPostInSection(reply.post);
			
		}else{
			alert(reply.msg);
		}
		
	}else{
		
		 alert("Ha ocurrido un error en la peticion con el servidor.");
	}
	
}


function insertPostInSection(post){
	
	var account=JSON.parse(localStorage.getItem('account'));
	var imgTagStyle="";
	
	if(post.postImg!=""){
		
	imgTagStyle="<section style='width:200px'>" +
					" <img src='"+post.postImg +"'  width='200' height='175'>" +
				"</section>";
	}
	

	$("#allPosts").prepend("<br>" +
							"<div class='myPost' id='"+post.postId+"'> " +
                         	 	"<div class='erasePostBtn'> " +
                         	 		"<input type='button' class='deletePostBtn' value='Eliminar Publicación'>" +
                         		"</div>"+
                         		
                         		"<div class='postHeader'>"+
                         			"<image class='userPicPost' src='"+account.profPicture+"'></image>"+
                         			"<div class='userNamePost'>"+account.nickName+"</div><br>"+
                         			"<div class='datePost'>"+post.postDate.year+"-"+post.postDate.month+"-"+post.postDate.day+"</div>"+
                         		"</div>"+
                         		
                         		"<div class='postBody'>"+
                         			"<div class='contentPost'><p>"+post.postContent+"</p></div>"+ imgTagStyle +
                         		"</div>"+
                         		
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

	$(".deletePostBtn").click(deletePost);
	$(".doComment").click(commentPost);

}

function commentPost(){
	
	 var textArea=$(this).parent().prev("textarea");
	 var content= textArea.val();
	 var idPost=$(this).parent().parent().parent().parent().prop("id");
	 
	 $(this).attr("id","commentId");// tengo que hacerlo para tener una referencia desp del callback
	 
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
    		
    		sendCommentRequest(JSON.stringify(commentBody));
   		
    	}
	 
	 textArea.val("");
	 
	}

function sendCommentRequest(jsonComment){
	var request= new XMLHttpRequest();
	 
	request.open('POST','http://localhost:8080/WebProyect/rest/post/makeComment');
	request.setRequestHeader("Content-Type","application/json");
	request.onload= () => makeCommentCallback(request);
	    
	 try{
	    request.send(jsonComment);
	    	
	 }catch(Error){
	    alert("Ocurrio el siguiente Error: " + error);
	 }
	
}

function makeCommentCallback(response){
	
	if(response.status==200){
		
		var reply=JSON.parse(response.responseText);
		
		if(reply.success){
			
			insertComment(reply.comment);
			
		}else{
			alert(reply.msg);
		}
		
	}else{
		
		 alert("Ha ocurrido un error en la peticion con el servidor.");
	}
		
}

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


function getPosts(){
	
	var account=JSON.parse(localStorage.getItem('account'));
	
	var idBody={
		id:account.id
	}
	
	var request= new XMLHttpRequest();
	request.open('POST','http://localhost:8080/WebProyect/rest/post/getPosts');
	request.setRequestHeader("Content-Type","application/json");
	request.onload= () => getPostsCallBack(request);
	    
	 try{
		 
	    request.send(JSON.stringify(idBody));
	    	
	 }catch(Error){
	    alert("Ocurrio el siguiente Error: " + error);
	 }
	
}

function getPostsCallBack(response){
	
	if(response.status==200){
		
		var posts=JSON.parse(response.responseText);
		
		if(posts!=null){
			
			var account=JSON.parse(localStorage.getItem('account'));
			
			for(var i=0;i<posts.length;i++){
				
				var commentsTags="";
				
				if(posts[i].comments!=null){
					
					for(var j=0; j<posts[i].comments.length ;j++){
						
						if(posts[i].comments[j]!=null){
							
						commentsTags+= ' <div class="postComment" id="'+posts[i].comments[j].commentId+'">'+
						'<div class="commentHeader">'+
							'<image class="userPicComment"src="'+posts[i].comments[j].profPic+'"></image>'+
							'<div class="userNameComment">'+posts[i].comments[j].nickName+'</div><br>' + 
							'<div class="dateComment">'+posts[i].comments[j].commentDate.year+"-"+posts[i].comments[j].commentDate.month+"-"+
							 posts[i].comments[j].commentDate.day+'</div>'+
						'</div>'+
						'<div class="commentBody">'+		
							'<div class="commentContent">'+
								'<p>'+posts[i].comments[j].content+'</p>'+
							'</div> '+	   		   
						'</div>'+  	 
						'<div class="eraseCommentBtn">'+		
							'<input type="button" class="deleteComment" value="Eliminar Comentario">'+    
						'</div>'+
					'</div> '
					
						}
					}
					
				}
				
				var imgTagStyle="";
				
				if(posts[i].postImg!=""){
					
					imgTagStyle=" <section style='width:200px'>" +
									" <img src='"+posts[i].postImg +"'  width='200' height='175'>" +
								"</section> ";
					}
				
				$("#allPosts").prepend("<br>" +
				"<div class='myPost' id='"+posts[i].postId+"'> " +
             	 	"<div class='erasePostBtn'> " +
             	 		"<input type='button' class='deletePostBtn' value='Eliminar Publicación'>" +
             		"</div>"+
             		
             		"<div class='postHeader'>"+
             			"<image class='userPicPost' src='"+ account.profPicture +"'></image>"+
             			"<div class='userNamePost'>"+account.nickName+"</div><br>"+
             			"<div class='datePost'>"+posts[i].postDate.year+"-"+posts[i].postDate.month+"-"+posts[i].postDate.day+"</div>"+
             		"</div>"+
             		
             		"<div class='postBody'>"+
             			"<div class='contentPost'><p>"+posts[i].postContent+"</p></div>"+ imgTagStyle +
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
			$(".deletePostBtn").click(deletePost);
			$(".doComment").click(commentPost);
		}
		
	}else{
		
		 alert("Ha ocurrido un error en la peticion con el servidor.");
	}
	
}

function deletePost(){
	
	if(window.confirm("Seguro que desea borrar esta publicacion?")){
		var idPost=$(this).parent().parent().prop("id");
		$(this).parent().parent().attr("id","deletePost");
		
		var idPost={
			postId:idPost
		}
		
		var request= new XMLHttpRequest();
		request.open('POST','http://localhost:8080/WebProyect/rest/post/deletePost');
		request.setRequestHeader("Content-Type","application/json");
		request.onload= () => {
		
			if(request.status==200){
				
				if(JSON.parse(request.responseText)){
					$("#deletePost").remove();
					
				}else{
					alert("Ocurrio un error y no se pudo borrar la publicacion.");
				}
				
			}else{
				alert("Ha ocurrido un error en la peticion con el servidor.");
			}
			
		}
		
		try{
		    request.send(JSON.stringify(idPost));
		    	
		 }catch(Error){
		    alert("Ocurrio el siguiente Error: " + error);
		 }
	}
}

function deleteComment(){
	
	if(window.confirm("Realmente desea borrar el comentario?")){
		
		var idComment=$(this).parent().parent().prop("id");
		
		$(this).parent().parent().attr("id","deleteComment");//le cambio el id para borrarlo despues
			
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

function resetPostArea(){
	
	$("#postImg").prop("src","#");
	$("#postImg").css("display","none");
	$("#areaFile").css("display","none");
	$("#postContent").val("");
	
}

