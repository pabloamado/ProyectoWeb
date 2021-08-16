package mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dao.Dao;
import entity.Comment;
import entity.ExceptionWriter;
import entity.Post;
import helper.Helper;

public class PostMapper {

	private Dao dao;
	private Helper helper;

	public PostMapper(Dao postDao, Helper helper) {
		this.dao = postDao;
		this.helper = helper;
	}

	// GUARDA LA PUBLICACION EN LA DB LISTO
	public int savePost(Post post) {
		int idGenerated = 0;
		HashMap<Integer, Object> postMapper = new HashMap<>();

		postMapper.put(1, post.getPostContent());
		postMapper.put(2, post.getPostDate());
		postMapper.put(3, post.getAccountId());

		String query = "insert into social.post (post_content,post_date,acc_id) values (?,?,?)";

		if (post.getPostImg() != "") {

			postMapper.put(4, post.getPostImg());
			query = "insert into social.post (post_content,post_date,acc_id,post_image) values (?,?,?,?)";
		}

		try {

			idGenerated = dao.getIdGenerated(query, postMapper);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return idGenerated;
	}

	// GUARDA UN COMENTARIO EN LA DB LISTO
	public int saveComment(Comment comment) {
		int idGenerated = 0;
		HashMap<Integer, Object> commentMapper = new HashMap<>();
		commentMapper.put(1, comment.getContent());
		commentMapper.put(2, comment.getCommentDate());
		commentMapper.put(3, comment.getPostId());
		commentMapper.put(4, comment.getComOwnerId());

		String query = "insert into social.comment(com_content,com_date,post_id,com_owner_id)" + " values (?,?,?,?)";

		try {

			idGenerated = dao.getIdGenerated(query, commentMapper);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return idGenerated;
	}

	// OBTENGO EL LISTADO DE POST COMPLETO(CON COMENTARIOS)
	public ArrayList<Post> getPosts(int idAccount) {

		List<HashMap<String, Object>> postList = null;
		ArrayList<Post> posts;
		HashMap<Integer, Object> postMapper = new HashMap<>();

		postMapper.put(1, idAccount);

		String query = "select * from social.post where acc_id=? order by post_date asc";

		try {

			postList = dao.getEntity(query, postMapper);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		posts = helper.obtainPosts(postList);

		setPostComments(posts);

		return posts;
	}

	// OBTENGO EL LISTADO DE COMENTARIOS SEGUN EL ID DEL POST
	private ArrayList<Comment> getComments(int postId) {

		ArrayList<Comment> comments;
		List<HashMap<String, Object>> commentList = null;
		HashMap<Integer, Object> postIdMap = new HashMap<>();
		postIdMap.put(1, postId);

		String query = "select c.com_id,c.com_content, c.com_owner_id, c.com_date,a.acc_prof_image, a.acc_nickname "
				+ "from social.comment c inner join social.account a on a.acc_id=c.com_owner_id where c.post_id=? "
				+ "order by c.com_date asc";

		try {

			commentList = dao.getEntity(query, postIdMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		comments = helper.obtainComments(commentList);

		return comments;
	}

	// BORRA UN POST COMPLETO CON SUS COMENTARIOS (USA UNA TRANSACCION)
	public boolean deletePost(int idPost) {
		boolean deleted = false;
		String query1 = "delete from social.comment where post_id=?";
		String query2 = "delete from social.post where post_id=?";
		HashMap<Integer, Object> commentMapper1 = new HashMap<>();
		HashMap<Integer, Object> commentMapper2 = new HashMap<>();

		commentMapper1.put(1, idPost);
		commentMapper2.put(1, idPost);

		try {
			deleted = dao.deletePostTransaction(query1, query2, commentMapper1, commentMapper2);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return deleted;

	}

	// BORRA UN COMENTARIO DE LA DB SEGUN EL ID
	public boolean deleteComment(int idCom) {
		boolean deleted = false;
		HashMap<Integer, Object> commentMapper = new HashMap<>();
		commentMapper.put(1, idCom);

		String query = "delete from social.comment where com_id=?";

		try {

			deleted = dao.doOperation(query, commentMapper);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return deleted;
	}

	// SETEO LA LISTA DE COMENTARIOS POR CADA POST QUE TENGA EL USUARIO
	private void setPostComments(ArrayList<Post> posts) {
		ArrayList<Comment> comments;

		if (posts != null) {

			for (int i = 0; i < posts.size(); i++) {

				comments = getComments(posts.get(i).getPostId());
				posts.get(i).setComments(comments);
			}

		}

	}

	// GUARDA LA IMAGEN EN LA DB LISTO
	public boolean saveImg(int accountId, String profPic) {
		boolean saved = false;
		HashMap<Integer, Object> imgMap = new HashMap<Integer, Object>();

		imgMap.put(1, profPic);
		imgMap.put(2, accountId);

		String query = "update social.account set acc_prof_image=? where acc_id=?";

		try {
			saved = dao.doOperation(query, imgMap);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return saved;
	}

}
