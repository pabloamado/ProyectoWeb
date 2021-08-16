package response;

import entity.Post;

public class PostResponse extends GenericResponse {

	private Post post;
	
	public Post getPost() {
		return post;
	}
	
	public void setPost(Post post) {
		this.post = post;
	}
	
	
}
