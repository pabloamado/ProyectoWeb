package mapper;

import java.util.HashMap;
import java.util.List;

import dao.Dao;
import entity.ExceptionWriter;
import helper.Helper;

public class CodeMapper {

	private Dao dao;
	private Helper helper;

	public CodeMapper(Dao dao, Helper helper) {
		this.dao = dao;
		this.helper = helper;
	}

	// PREPARA EL MAPPER PARA GUARDAR EL CODIGO GENERADO PARA CAMBIAR LA CONTRASEÑA
	// POSTERIORMENTE
	public boolean saveCode(int accountId, String code) {

		boolean saved = false;
		HashMap<Integer, Object> codeMap = new HashMap<>();
		codeMap.put(1, code);
		codeMap.put(2, accountId);

		String query = "update social.account set acc_code=? where acc_id =?";

		try {

			saved = dao.doOperation(query, codeMap);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return saved;
	}

	//PREPARA EL MAPPER PARA RESETEAR EL CODIGO GENERADO
	public boolean restartCode(String mail) {
		boolean success=false;
		HashMap<Integer, Object> codeMap = new HashMap<>();
		codeMap.put(1, "0");
		codeMap.put(2, mail);

		String query = "update social.account set acc_code=?   where acc_mail=?";

		try {

		 success=dao.doOperation(query, codeMap);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return success;
	}

	//PREPARA EL  MAPPER PARA OBTENER EL CODIGO POR MEDIO DEL MIL
	public String getCodeWithMail(String mail) {

		List<HashMap<String, Object>> codeListMap = null;
		HashMap<Integer, Object> mailMap = new HashMap<>();
		mailMap.put(1, mail);

		String query = "select  acc_code from social.account  where acc_mail=?";

		try {

			codeListMap = dao.getEntity(query, mailMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return helper.obtainAccountCode(codeListMap);
	}

}
