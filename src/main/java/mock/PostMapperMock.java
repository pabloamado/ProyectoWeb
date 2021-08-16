package mock;

import java.util.ArrayList;

import dao.Dao;
import entity.Comment;
import entity.Post;
import helper.Helper;
import mapper.PostMapper;

public class PostMapperMock extends PostMapper{

	private boolean success;
	private int idGenerated;
	private ArrayList<Post> posts;
	
	public PostMapperMock(Dao postDao, Helper helper) {
		super(postDao, helper);
	}
	
	public void setSuccess(boolean success) {
		this.success=success;
	}

	public void setIdGenerated(int idGenerated) {
		this.idGenerated = idGenerated;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	@Override
	public boolean deleteComment(int idCom) {
		
		return success;
	}
	
	@Override
	public boolean deletePost(int idPost) {
	
		return success;
	}
	
	@Override
	public boolean saveImg(int accountId, String profPic) {

		return success;
	}
	
	@Override
	public int saveComment(Comment comment) {

		return idGenerated;
	}
	
	@Override
	public int savePost(Post post) {

		return idGenerated;
	}
	
	@Override
	public ArrayList<Post> getPosts(int idAccount) {
	
		return posts;
	}
	
}
