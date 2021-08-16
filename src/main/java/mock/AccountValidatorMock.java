
package mock;

import java.time.LocalDate;
import entity.Account;
import validator.AccountValidator;

public class AccountValidatorMock  extends AccountValidator{
    
    private boolean valid;
    private boolean equal;
    
    public void setValid(boolean valid){
        this.valid=valid;
    }
    
    public void setEqual(boolean equal) {
    	this.equal=equal;
    }
    
    public boolean userIsValid(Account user){
        return valid;
    }
    
    public boolean passwordIsValid(String password){
        return valid;
    }
    public boolean nameIsValid(String name){
        return valid;
    }
    
    public boolean nickNameIsValid(String nickname){
        return valid;
    }
    
    public boolean mailIsValid(String mail){
        return valid;
    }
    
    public boolean hasLegalAge(LocalDate date){
        return valid;
    }

    public boolean isUserIsValid() {
        return valid;
    }
  
    public void setUserIsValid(boolean valid) {
        this.valid = valid;
    }
    
    @Override
    public boolean equals(String string1, String string2) {
    	return equal;
    }
    
}
