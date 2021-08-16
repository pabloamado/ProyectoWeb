package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import mapper.CodeMapper;
import mock.DaoMock;

class CodeMapperTest {

	private DaoMock dao=new DaoMock();
	private CodeMapper codeMapper=new CodeMapper(dao, null);
	
	@Test
	final void testSaveCodeSuccess() {
		dao.setSaved(true);
		boolean result=codeMapper.saveCode(1, "asdasdas");
		assertTrue(result);
		
	}
	
	@Test
	final void testSaveCodeFailed() {
		dao.setSaved(false);
		boolean result=codeMapper.restartCode("asdasdas");
		assertFalse(result);
		
	}

	@Test
	final void testRestartCodeSuccess() {
		dao.setSaved(true);
		boolean result=codeMapper.restartCode("asdasdas");
		assertTrue(result);
		
	}
	
	@Test
	final void testRestartCodeFalse() {
		dao.setSaved(false);
		boolean result=codeMapper.restartCode("asdasdas");
		assertFalse(result);
	}

}
