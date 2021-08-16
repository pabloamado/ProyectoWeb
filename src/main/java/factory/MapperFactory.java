package factory;

import dao.Dao;
import helper.Helper;
import mapper.AccountMapper;
import mapper.CodeMapper;
import mapper.LoginMapper;
import mapper.PostMapper;
import mapper.RequestMapper;

public class MapperFactory {

	
	public static AccountMapper createAccountMapper() {
		
		return new AccountMapper(new Dao(), new Helper());
	}
	
public static LoginMapper createLoginMapper() {
		
	return new LoginMapper(new Dao());
	}
public static CodeMapper createCodeMapper() {
	
	return new CodeMapper(new Dao(), new Helper());
}

public static PostMapper createPostMapper() {
	
	return new PostMapper(new Dao(), new Helper());
}

public static RequestMapper createRequestMapper() {
	
	return new RequestMapper(new Dao(), new Helper());
}
}
