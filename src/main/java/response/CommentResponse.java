package response;

import entity.Comment;

public class CommentResponse extends GenericResponse {

	private Comment comment;
	
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}
