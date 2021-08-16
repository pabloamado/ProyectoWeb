package mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dao.Dao;
import entity.Account;
import entity.ExceptionWriter;
import helper.Helper;

public class AccountMapper{

	private Dao dao;
	private Helper helper;

	public AccountMapper(Dao dao, Helper helper) {
		this.dao = dao;
		this.helper = helper;
	}

	// PREPARA EL MAPPER PARA REGISTRAR (GUARDAR ) LA CUENTA EN LA DB
	public boolean saveAccount(Account account) {
		boolean saved = false;
		HashMap<Integer, Object> accountMap = new HashMap<>();

		accountMap.put(1, account.getName());
		accountMap.put(2, account.getLastName());
		accountMap.put(3, account.getBirthday());
		accountMap.put(4, account.getMail());
		accountMap.put(5, account.getPassword());
		accountMap.put(6, account.getNickName());
		accountMap.put(7, account.getAttempts());
		accountMap.put(8, account.isBlocked());
		accountMap.put(9, account.isDeleted());
		accountMap.put(10, null);
		String query = "INSERT INTO social.account (acc_name,acc_lastname,acc_birthday,acc_mail,acc_password,acc_nickname,"
				+ "acc_attempts, acc_blocked, acc_deleted, acc_prof_image) VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {

			saved = dao.doOperation(query, accountMap);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return saved;

	}

	// PREPARA EL MAPPER PARA ACTUALIZAR LOS DATOS DE LA CUENTA ( SOLO AL REACTIVAR
	// LA CUENTA BORRADA)
	public boolean updateAccount(Account account) {
		boolean updated = false;
		HashMap<Integer, Object> accountMap = new HashMap<>();
		accountMap.put(1, account.getName());
		accountMap.put(2, account.getLastName());
		accountMap.put(3, account.getBirthday());
		accountMap.put(4, account.getPassword());
		accountMap.put(5, account.getNickName());
		accountMap.put(6, account.getId());

		String query = "update social.account set acc_name=?, acc_lastname=?, acc_birthday=?,  acc_password=?,"
				+"acc_nickname=?, acc_deleted=false where acc_id=?";

		try {
			updated = dao.doOperation(query, accountMap);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return updated;
	}

	// PREPARA EL MAPPER PARA ACTUALIZAR LA CUENTA A ESTADO BORRADO Y NO PERMITE EL
	// ACCESO
	public boolean deleteAccount(int accountId) {

		boolean deleted = false;
		HashMap<Integer, Object> accountMap = new HashMap<>();

		accountMap.put(1, accountId);

		String query = "update social.account set acc_deleted=true where acc_id=?";

		try {
			deleted = dao.doOperation(query, accountMap);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return deleted;
	}

	// PREPARA EL MAPPER PARA ACTUALIZAR EL NICK EN LA DB
	public boolean updateNickname(String nickname, int accountId) {
		boolean updated = false;
		HashMap<Integer, Object> userMap = new HashMap<Integer, Object>();

		userMap.put(1, nickname);
		userMap.put(2, accountId);

		String query = "update social.account set acc_nickname=? where acc_id=?";

		try {
			updated = dao.doOperation(query, userMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return updated;
	}

	// ACTUALIZA  EL MAIL DE LA CUENTA, A PARTIR DEL MAIL ACTUAL
	public boolean updateMail(String newMail, int accountId) {
		boolean updated = false;
		HashMap<Integer, Object> accountMap = new HashMap<Integer, Object>();

		accountMap.put(1, newMail);
		accountMap.put(2, accountId);
		String query = "update social.account set acc_mail=? where acc_id=?";

		try {
			updated = dao.doOperation(query, accountMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}
		return updated;
	}

	// ACTUALIZA LA CONTRASEÑA DE LA CUENTA,RECIBE POR PARAMETRO UN FLAG PARA VER SI SE ACTUALIZA
	//ESTANDO EL USUARIO LOGUEADO O NO
	public boolean updatePassword(String newPass, String mail, int flag) {
		boolean updated = false;
		HashMap<Integer, Object> passwordMap = new HashMap<>();

		passwordMap.put(1, newPass);
		passwordMap.put(2, mail);
		String query;

		if (flag == 0) {
			query = "update social.account set acc_password =?  where acc_mail =?";
		} else {
			query = "update social.account set acc_password =?, acc_blocked=false, acc_attempts=0 where acc_mail =?";
		}

		try {
			updated = dao.doOperation(query, passwordMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return updated;
	}

	// OBTIENE LA CUENTA  A PARTIR DEL EMAIL PROPORCIONADO
	public Account getAccountWithMail(String mail) {

		HashMap<Integer, Object> mailMap = new HashMap<>();
		List<HashMap<String, Object>> accountListMap = null;
		mailMap.put(1, mail);

		String query = "select acc_id, acc_name, acc_lastname, acc_birthday, acc_mail, acc_password,acc_nickname, "
				+ "acc_attempts, acc_blocked, acc_deleted, acc_prof_image  from social.account where acc_mail =?";

		try {
			accountListMap = dao.getEntity(query, mailMap);
		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return helper.obtainAccount(accountListMap);
	}

	// OBTIENE LA CUENTA A PARTIR DEL ID PROPORCIONADO
	public Account getAccountWithId(int accountId) {

		HashMap<Integer, Object> idMap = new HashMap<>();
		List<HashMap<String, Object>> registerList = null;
		idMap.put(1, accountId);

		String query = "select acc_id,acc_name, acc_lastname, acc_birthday, acc_mail, acc_password,acc_nickname, "
				+ "acc_attempts, acc_blocked, acc_deleted  from social.account where acc_id=?";

		try {
			registerList = dao.getEntity(query, idMap);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return helper.obtainAccount(registerList);

	}

	// OBTENGO LOS USERS MENOS EL ID QUE LE PASO POR PARAMETRO
	public ArrayList<Account> getUsers(int accountId) {
		
		List<HashMap<String, Object>> registerList=null;
		HashMap<Integer, Object> idMap = new HashMap<>();
		idMap.put(1, accountId);
		
		String query=" select * from getAccounts(?)";
		
		/*create or replace function getAccounts(id integer) 
		returns table (acc_id integer,acc_nickname varchar,acc_prof_image varchar) 
		language plpgsql
		as $$
		declare 
		 resultSet record;
		begin
			
			if(exists(select 1 from getFriendsIds(id))) then
			
				for resultSet in (
					select 
						a.acc_id as myId,
						a.acc_nickname as nick,
						a.acc_prof_image  as image
					from social.account a 
					inner join(select friendsIds from getFriendsIds(id)) as ids on ids.friendsIds<>a.acc_id 
					where a.acc_id<>id and a.acc_deleted=false
			) loop  acc_id :=resultSet.myId;
					acc_nickName :=resultSet.nick;
					acc_prof_image :=resultSet.image;
					return next;
			end loop;	
			
			else
			
				for resultSet in (
				select  
					a.acc_id as myId,
					a.acc_nickname as nick,
					a.acc_prof_image as image
				from social.account a 
				where a.acc_id<>id and a.acc_deleted=false
			) loop  acc_id :=resultSet.myId;
					acc_nickName :=resultSet.nick;
					acc_prof_image :=resultSet.image;
					return next;
			end loop;
			end if;
		end; $$ 

		create or replace function getFriendsIds(id integer) 
		returns table ( friendsIds integer) 
		language plpgsql
		as $$
		declare 
		 resultSet record;
		begin
			
			for resultSet in (
				select 
					acc_id as ids 
				 from social.friendship  
				 where fri_user_receiver_id=id
				 union  
				 select 
					fri_user_receiver_id  
				from social.friendship  
				where acc_id=id
			) loop  friendsIds:=resultSet.ids;
					return next;
			end loop;
		end;$$ */
		try {
			
			registerList = dao.getEntity(query,idMap);	
			
		}catch(Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		ArrayList<Account> users=helper.obtainUsersList(registerList);

		return users;

	}

	//PREPARA EL MAPPER PARA OBTENER LOS DATOS PARCIALES DE UN AMIGO
	public Account getFriendAccountWithId(int friendId) {
		
		List<HashMap<String, Object>> registerList=null;
		HashMap<Integer, Object> idMap = new HashMap<>();
		idMap.put(1, friendId);
	
		String query="select acc_id,acc_nickname,acc_prof_image from social.account where acc_id=?";
		try {
			
			registerList = dao.getEntity(query,idMap);	
			
		}catch(Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}
		
		ArrayList<Account> users=helper.obtainUsersList(registerList);
		
		return users.get(0);
	}
	

}
