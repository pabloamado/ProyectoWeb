
// FUNCIONA: FUNCION PARA DESLOGUEARSE
function logout(e){
    
    e.preventDefault();
    
	if(window.confirm("Realmente desea salir?")){
		
		window.localStorage.removeItem('friend');
		window.localStorage.removeItem('account');
		window.location.href="http://localhost:8080/WebProyect";	
		
	}
		
}
