package helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import entity.Account;
import entity.Comment;
import entity.Request;
import entity.Post;

public class Helper {

	// DEVUELVE LA CUENTA CON SUS RESPECTIVOS VALORES DEL MAPA
	public Account obtainAccount(List<HashMap<String, Object>> accountListMap) { // DEVUELVE EL ACCOUNT OBTENIDO

		Account account = null;

		if (accountListMap != null && accountListMap.get(0) != null) {

			account = accountParse(accountListMap.get(0));
		}

		return account;
	}

	// OBTIENE TODOS LOS DATOS DE UN CUENTA DEL MAPA
	private Account accountParse(HashMap<String, Object> registerMap) {
		Account account = new Account();

		account.setProfPicture("");
		account.setName(registerMap.get("acc_name").toString());
		account.setDeleted((boolean) registerMap.get("acc_deleted"));
		account.setBlocked((boolean) registerMap.get("acc_blocked"));
		account.setLastName(registerMap.get("acc_lastname").toString());
		account.setPassword(registerMap.get("acc_password").toString());
		account.setNickName(registerMap.get("acc_nickname").toString());
		account.setMail(registerMap.get("acc_mail").toString().toLowerCase());
		account.setId(Integer.valueOf(registerMap.get("acc_id").toString()).intValue());
		account.setAttempts(Integer.valueOf(registerMap.get("acc_attempts").toString()));

		if (registerMap.get("acc_prof_image") != null) {
			account.setProfPicture(registerMap.get("acc_prof_image").toString());
		}

		return account;
	}

	// OBTIENE UN LISTADO DE CUENTAS
	public ArrayList<Account> obtainUsersList(List<HashMap<String, Object>> usersMap) {
		ArrayList<Account> users = null;

		if (usersMap != null) {
			users = new ArrayList<>();

			for (HashMap<String, Object> user : usersMap) {

				users.add(userParse(user));
			}
			
		}

		return users;
	}

	// OBTIENE PARCIALMENTE LOS DATOS DE UNA CUENTA DEL MAPA
	private Account userParse(HashMap<String, Object> user) {
		Account account = new Account();

		account.setProfPicture("");
		account.setNickName(user.get("acc_nickname").toString());
		account.setId(Integer.valueOf(user.get("acc_id").toString()).intValue());

		if (user.get("acc_prof_image") != null) {
			account.setProfPicture(user.get("acc_prof_image").toString());
		}

		return account;
	}

	// OBTIENE EL CODIGO DE RECUPERACION DE LA CUENTA
	public String obtainAccountCode(List<HashMap<String, Object>> codeMap) {
		String code =null;

		if (codeMap != null && !codeMap.isEmpty()) {

			code = codeMap.get(0).get("acc_code").toString();

		}

		return code;
	}

	// OBTIENE LA LISTA DE SOLICITUDES DE AMISTAD
	public ArrayList<Request> obtainRequests(List<HashMap<String, Object>> requestsMap) {

		ArrayList<Request> requests = null;

		if (requestsMap != null) {

			requests = new ArrayList<>();

			for (HashMap<String, Object> req : requestsMap) {
				Request request = new Request();

				request.setProfPic("");
				request.setNicknameUserSender(req.get("acc_nickname").toString());
				request.setRequestId(Integer.valueOf(req.get("fri_id").toString()).intValue());
				request.setIdUserSender(Integer.valueOf(req.get("acc_id").toString()).intValue());

				if (req.get("acc_prof_image") != null) {
					request.setProfPic(req.get("acc_prof_image").toString());
				}

				requests.add(request);
			}
		}
		return requests;
	}

	// OBTIENE LA LISTA DE PUBLICACIONES
	public ArrayList<Post> obtainPosts(List<HashMap<String, Object>> postList) {

		ArrayList<Post> posts = null;

		if (postList != null) {

			posts = new ArrayList<>();

			for (HashMap<String, Object> p : postList) {
				Post post = new Post();

				post.setPostContent(p.get("post_content").toString());
				post.setPostDate(LocalDate.parse(p.get("post_date").toString()));
				post.setPostId(Integer.valueOf(p.get("post_id").toString()).intValue());

				if (p.get("post_image") != null) {
					post.setPostImg(p.get("post_image").toString());
				}

				posts.add(post);
			}

		}

		return posts;
	}

	// OBTIENE LA LISTA DE COMENTARIOS
	public ArrayList<Comment> obtainComments(List<HashMap<String, Object>> commentList) {

		ArrayList<Comment> comments = null;

		if (commentList != null) {

			comments = new ArrayList<>();

			for (HashMap<String, Object> c : commentList) {
				Comment comment = new Comment();

				comment.setProfPic("");
				comment.setContent(c.get("com_content").toString());
				comment.setNickName(c.get("acc_nickname").toString());
				comment.setCommentDate(LocalDate.parse(c.get("com_date").toString()));
				comment.setCommentId(Integer.valueOf(c.get("com_id").toString()).intValue());
				comment.setComOwnerId(Integer.valueOf(c.get("com_owner_id").toString()).intValue());

				if (c.get("acc_prof_image") != null) {
					comment.setProfPic(c.get("acc_prof_image").toString());
				}

				comments.add(comment);
			}
		}
		return comments;
	}

	// PREPARA Y DEVUELVE UNA LISTA CON TODOS LOS AMIGOS
	public ArrayList<Request> obtainFriends(List<HashMap<String, Object>> friends) {

		ArrayList<Request> friendList = null;

		if (friends != null) {

			friendList = new ArrayList<Request>();

			for (HashMap<String, Object> f : friends) {

				Request friend = new Request();

				friend.setProfPic("");
				friend.setNicknameUserSender(f.get("acc_nickname").toString());
				// usado a futuro para borrar a un amigo
				friend.setRequestId(Integer.valueOf(f.get("fri_id").toString()).intValue());
				// solo en este caso usamos esta variable para setear los ids de mis amigos
				friend.setIdUserSender(Integer.valueOf(f.get("acc_id").toString()).intValue());

				if (f.get("acc_prof_image") != null) {
					friend.setProfPic(f.get("acc_prof_image").toString());
				}

				friendList.add(friend);
			}

		}

		return friendList;
	}

	// OBTIENE LA IMAGEN DEL MAP
	public String parseImage(List<HashMap<String, Object>> image) {
		String imagePath = null;

		if (image != null && image.get(0).get("acc_prof_image") != null) {

			imagePath = image.get(0).get("acc_prof_image").toString();

		}

		return imagePath;
	}

}