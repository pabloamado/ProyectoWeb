package mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dao.Dao;
import entity.ExceptionWriter;
import entity.Request;
import entity.RequestState;
import helper.Helper;

public class RequestMapper {

	private Dao dao;
	private Helper helper;

	public RequestMapper(Dao dao, Helper helper) {
		this.dao = dao;
		this.helper = helper;

	}

	// OBTIENE LAS SOLICITUDES DE AMISTAD
	public ArrayList<Request> getRequests(int accountId) {

		List<HashMap<String, Object>> requests = null;
		HashMap<Integer, Object> idMap = new HashMap<>();
		idMap.put(1, accountId);
		idMap.put(2, RequestState.PENDING.toString());

		String query = "select a.acc_id, a.acc_nickname, a.acc_prof_image,sub.fri_id from social.account  a "
				+ " inner join (select  fri_id, acc_id from social.friendship "
				+ " where fri_user_receiver_id=?  and fri_state=?)sub on sub.acc_id=a.acc_id ";

		try {
			requests = dao.getEntity(query, idMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return helper.obtainRequests(requests);
	}

	// ENVIA UNA SOLICITUD DE AMISTAD
	public boolean sendRequest(int accountId, int userId) {
		
		boolean sended = false;
		HashMap<Integer, Object> requestMap = new HashMap<>();
		requestMap.put(1, RequestState.PENDING.toString());
		requestMap.put(2, accountId);
		requestMap.put(3, userId);

		String query = "insert into social.friendship (fri_state,acc_id, fri_user_receiver_id) " + " values (?,?,?) ";

		try {

			sended = dao.doOperation(query, requestMap);
			
		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return sended;
	}

	// RECHAZA O ACEPTA LA SOLICITUD DE AMISTAD
	public boolean sendAcceptRejectRequest(int requestId, RequestState flagRequest) {
		
		boolean success = false;
		HashMap<Integer, Object> requestMap = new HashMap<>();
		requestMap.put(1, requestId);

		String query = "delete from social.friendship where fri_id=?";

		if (flagRequest.equals(RequestState.ACCEPTED)) {

			query = "update social.friendship set fri_state='" + flagRequest + "' where fri_id=?";
		}

		try {
			success = dao.doOperation(query, requestMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();

		}

		return success;
	}

	// BORRA A UN AMIGO
	public boolean deleteFriend(int requestId) {
		
		boolean deleted = false;
		HashMap<Integer, Object> deleteFriendMap = new HashMap<>();
		deleteFriendMap.put(1, RequestState.ACCEPTED.toString());
		deleteFriendMap.put(2, requestId);

		String query = "delete from social.friendship where fri_state=? and fri_id=? ";

		try {

			deleted = dao.doOperation(query, deleteFriendMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return deleted;
	}

	// OBTIENE A LOS AMIGOS DEL USUARIO
	public ArrayList<Request> getFriends(int accountId) {

		List<HashMap<String, Object>> friends = null;
		HashMap<Integer, Object> idMap = new HashMap<>();
		idMap.put(1, RequestState.ACCEPTED.toString());
		idMap.put(2, accountId);
		idMap.put(3, RequestState.ACCEPTED.toString());
		idMap.put(4, accountId);

		String query = "select a.acc_nickname, a.acc_prof_image, a.acc_id, sub.fri_id "
				+ " from social.account a inner join (select  fri_user_receiver_id as friend_id, fri_id "
				+ " from social.friendship where fri_state=? and acc_id=? union "
				+ " select  acc_id, fri_id from social.friendship where fri_state=? and fri_user_receiver_id=?) "
				+ " sub on sub.friend_id=a.acc_id  where a.acc_deleted=false ";

		try {

			friends = dao.getEntity(query, idMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return helper.obtainFriends(friends);
	}

}