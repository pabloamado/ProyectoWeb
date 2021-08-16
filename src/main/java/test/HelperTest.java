package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import helper.Helper;

class HelperTest {
	
	private Helper helper=new Helper();

	@Test
	final void testObtainAccountNull() {
		List<HashMap<String, Object>> list=null;
		assertNull(helper.obtainAccount(list));
	}
	
	@Test
	final void testObtainAccountNotNull() {
		List<HashMap<String, Object>> accountList=new ArrayList<>();
		HashMap<String, Object> accountMap= new HashMap<>();
		accountMap.put("acc_name", "name");
		accountMap.put("acc_deleted", false);
		accountMap.put("acc_blocked", false);
		accountMap.put("acc_lastname", "lastname");
		accountMap.put("acc_password", "adasdasd2");
		accountMap.put("acc_mail", "ejemplo@gmail.com");
		accountMap.put("acc_nickname", "nickname");
		accountMap.put("acc_id", 1);
		accountMap.put("acc_attempts", 0);
		accountMap.put("acc_prof_image", "image");
		accountList.add(accountMap);
		assertNotNull(helper.obtainAccount(accountList));
		
	}

	@Test
	final void testObtainUsersListNull() {
		List<HashMap<String, Object>> list=null;
		assertNull(helper.obtainUsersList(list));
	}
	
	@Test
	final void testObtainUsersListNotNull() {
		List<HashMap<String, Object>>usersList=new ArrayList<>();
		HashMap<String, Object> userMap= new HashMap<>();
		userMap.put("acc_nickname", "nickname");
		userMap.put("acc_id", 1);
		userMap.put("acc_prof_image", "image");
		usersList.add(userMap);
		assertNotNull(helper.obtainUsersList(usersList));
	}

	@Test
	final void testObtainAccountCodeNull() {
		List<HashMap<String, Object>> list=null;
		assertNull(helper.obtainAccountCode(list));
	}
	
	@Test
	final void testObtainAccountCodeNotNull() {
		List<HashMap<String, Object>> codeList=new ArrayList<>();
		HashMap<String, Object> codeMap= new HashMap<>();
		codeMap.put("acc_code", "code");
		codeList.add(codeMap);
		assertNotNull(helper.obtainAccountCode(codeList));
	}

	@Test
	final void testObtainRequestsListMapNull() {
		List<HashMap<String, Object>> list=null;
		assertNull(helper.obtainRequests(list));
	}
	
	@Test
	final void testObtainRequestsListMapNotNull() {
		List<HashMap<String, Object>> requestList=new ArrayList<>();
		HashMap<String, Object> requestMap= new HashMap<>();
		requestMap.put("acc_nickname", "nickname");
		requestMap.put("fri_id", "1");
		requestMap.put("acc_id", "2");
		requestMap.put("acc_prof_image", "image");
		assertNotNull(helper.obtainRequests(requestList));
	}

	@Test
	final void testObtainPostsNull() {
		List<HashMap<String, Object>> list=null;
		assertNull(helper.obtainPosts(list));
	}
	
	@Test
	final void testObtainPostsNotNull() {
		List<HashMap<String, Object>> postList=new ArrayList<>();
		HashMap<String, Object> postMap= new HashMap<>();
		postMap.put("post_content", "sadsadas");
		postMap.put("post_date", "1");
		postMap.put("post_id", "1");
		postMap.put("post_image", "image");
		assertNotNull(helper.obtainPosts(postList));
	}

	@Test
	final void testObtainCommentsNull() {
		List<HashMap<String, Object>> list=null;
		assertNull(helper.obtainComments(list));
	}
	
	@Test
	final void testObtainCommentsNotNull() {
		List<HashMap<String, Object>>commentList=new ArrayList<>();
		HashMap<String, Object> commentMap= new HashMap<>();
		commentMap.put("com_id", "1");
		commentMap.put("com_content", "asdsadsad");
		commentMap.put("com_date", "1984-05-08");
		commentMap.put("acc_prof_image", "image");
		commentMap.put("acc_nickname", "nickname");
		commentMap.put("com_owner_id", "1");
		
		commentList.add(commentMap);
		assertNotNull(helper.obtainComments(commentList));
	}
	
	@Test
	final void testObtainFriendsNull() {
		List<HashMap<String, Object>> list=null;
		assertNull(helper.obtainFriends(list));
	}
	
	@Test
	final void testObtainFriendsNotNull() {
		List<HashMap<String, Object>> friendList=new ArrayList<>();
		HashMap<String, Object> friendMap= new HashMap<>();
		friendMap.put("acc_id", "1");
		friendMap.put("fri_id", "2");
		friendMap.put("acc_nickname", "nick");
		friendMap.put("acc_prof_image", "image");
		
		friendList.add(friendMap);
		
		assertNotNull(helper.obtainFriends(friendList));
	}

	@Test
	final void testParseImageNullListMapNull() {
		List<HashMap<String, Object>> list=null;
		assertNull(helper.parseImage(list));
		
	}
	
	@Test
	final void testParseImageNull() {
		List<HashMap<String, Object>> list=new ArrayList<>();
		HashMap<String, Object> imageMap= new HashMap<>();
		list.add(imageMap);
		
		assertNull(helper.parseImage(list));
	}
	
	@Test
	final void testParseImageNotNull() {
		List<HashMap<String, Object>> list=new ArrayList<>();
		HashMap<String, Object> imageMap= new HashMap<>();
		imageMap.put("acc_prof_image", "image");
		list.add(imageMap);
		
		assertNotNull(helper.parseImage(list));
	}

}
