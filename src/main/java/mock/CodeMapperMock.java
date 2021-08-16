package mock;

import dao.Dao;
import helper.Helper;
import mapper.CodeMapper;

public class CodeMapperMock extends CodeMapper {

	public CodeMapperMock(Dao dao, Helper helper) {
		super(dao, helper);
	
	}

	private boolean success;
	private String code;
	
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getCodeWithMail(String mail) {
	
		return code;
	}
	
	@Override
	public boolean restartCode(String mail) {
		
		return success;
	}
	
	@Override
	public boolean saveCode(int accountId, String code) {
		
		return success;
	}
}
