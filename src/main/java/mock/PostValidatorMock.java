package mock;

import validator.PostValidator;

public class PostValidatorMock extends PostValidator{

	private boolean valid;
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean contentIsValid(String content) {
		
		return valid;
	}
		
}
