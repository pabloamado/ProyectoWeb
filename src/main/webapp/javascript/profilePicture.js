$(document).ready(function(){
	
	
	$("#profilePhotoBtn").change(saveImage);
});

function saveImage(e){
	
	var imgs=e.target.files;
	
	var myImg=imgs[0];
	
	if(!myImg.type.match(/image/) ){
        
        alert("Selecciona una imagen por favor");
        
    } else if(myImg.size>3000000){
	
    	 alert("Seleccione una imagen que sea igual o inferior a 3 MB.");
    	 
    }else{
    	
		var reader=new FileReader();
	
		reader.readAsDataURL(myImg); 
			
		reader.addEventListener("load",saveProfPic,false);
	}
}

function saveProfPic(e){
	
	var imgPath=e.target.result;// EL LECTOR DEVUELVE LA IMAGEN EN BASE64
    var account=JSON.parse(localStorage.getItem('account'));
    
	var myImg={
			accountId:account.id,
			userImg:imgPath
	}
	
	sendImgRequest(JSON.stringify(myImg));
}

function sendImgRequest(jsonImg){
	
	var request=new XMLHttpRequest();
	 request.open('POST','http://localhost:8080/WebProyect/rest/post/saveImg');
	    request.setRequestHeader("Content-Type","application/json");
	    request.onload= () => imgCallback(request);
	    
	    try{
	    	request.send(jsonImg);
	    	
	    }catch(Error){
	    	
	    	alert("Ocurrio el siguiente Error: " + error);
	    }
}

function imgCallback(response){
	
	if(response.status=200){
		
		var reply=JSON.parse(response.responseText);
		
		if(reply.success){
			
			var account=JSON.parse(localStorage.getItem('account'));
			account.profPicture=reply.msg;
			localStorage.setItem('account',JSON.stringify(account));
			$("#imgProfile").prop("src",reply.msg);
		}
		
	}else{
		
		alert("Ha ocurrido un error inesperado con el servidor.");	
	}
	
	;
}

