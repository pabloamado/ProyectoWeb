 class Validator {

    nicknameIsValid(nickname) {
        var correctFormat=true;
        var pattern = new RegExp(/[`~,.<>;':"/[\]|{}()@!·$%&=_+-]/);
        
        if(pattern.test(nickname)|| nickname==''){
            window.alert("El apodo no puede estar vacio ni tener caracteres especiales.");
            correctFormat=false;
        }
        return correctFormat;

    }

    mailIsValid(mail) {
        var correctFormat=true;
        var chain = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]\n\
        {1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        var pattern = new RegExp(chain);
        
        if(mail==''){
            window.alert("El campo del mail no puede estar vacio.");   
             correctFormat=false;
           }
        
        if(!pattern.test(mail) ){
        	window.alert("Ingrese un mail valido.");   
        	correctFormat=false;
        }
        return correctFormat;
    }

    nameIsValid(name) {
        var correctFormat=true;
        var namePattern1 = "[' ']*";
        var namePattern2 = "[a-zA-Z' ']*";
        var pattern1 = new RegExp(namePattern1);
        var pattern2 = new RegExp(namePattern2);
        
        if(pattern1.test(name) && !pattern2.test(name) || name==''){
            window.alert("El nombre no puede estar vacio ni tener caracteres numericos ni especiales.");
            correctFormat=false;
        }

        return correctFormat;
    }

    passwordIsValid(password) {
        var correctFormat=true;
        var passPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
        var pattern = new RegExp(passPattern);
        
        if(!pattern.test(password) || password==''){
            window.alert("El campo de la contraseña no puede estar vacio y recuerda que debe" +
            		" tener por lo menos un numero y un minimo de 6 caracteres");
            correctFormat=false;
        }
        return correctFormat;
    }

    equalPassword(password, repeatedPassword) {
        var equal=true;
        if(password.localeCompare(repeatedPassword) != 0){
            
            window.alert("Las contraseñas introducidas no son identicas.");
            equal=false;
        }
        
        return equal;
    }
    
    notEqualPassword(password, repeatedPassword) {
   
        return password.localeCompare(repeatedPassword) != 0;
    }

    equalMail(mail, repeatedMail) {
        var equal=true;
        if(mail.localeCompare(repeatedMail) != 0){
            
            window.alert("Los mails introducidos no son iguales.");
            equal=false;
        }
        
        return equal;
    }
    
    notEqualMail(myMail,newMail) {
        var notEqual=true;
    	if(myMail.localeCompare(newMail) == 0){
    		notEqual=false;
    		alert("El nuevo mail debe ser distinto al actual");
    	}
        return notEqual;
    }

   	birthdayIsValid(birthday) {
   		var valid=false
   		if(birthday!=''){
   			
   			var date=new Date(birthday);      
   			var year=date.getUTCFullYear();
   			var month=date.getUTCMonth()+1;
   			var day=date.getUTCDate();
   			
         
   			valid = year>= 1930 && year < 2005;
   			valid &= month >= 1 && month <= 12;
   			valid &= day >= 1 && day <= 31;
        
   			if(month==2 && day>28 && isLeapYear(year)) {
   				valid=false;
   			}
         
   			if(!valid){
   				window.alert("No tienes la edad suficiente para registrarte.");
   			}
                 
   		}else{
   			
   			window.alert("No ha ingresado su fecha de nacimiento.");
   		}
         
        return valid;

    }
    
    isLeapYear(year){
     return year % 4==0 && year % 100!=0;   
    }
     
	validateRegister(name,lastName,birthday,mail1,mail2,password1,password2,nickname){
	
	
    var valid=true;
    
    valid &= this.nameIsValid(name);
    valid &= this.nameIsValid(lastName);
    valid &= this.birthdayIsValid(birthday);
    valid &= this.mailIsValid(mail1, mail2);
    valid &= this.passwordIsValid(password1, password2);
    valid &= this.nicknameIsValid(nickname);
    valid &= this.equalMail(mail1,mail2);
    valid &= this.equalPassword(password1,password2);
	
	return valid;
}
	
}