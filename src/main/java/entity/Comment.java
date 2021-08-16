	package entity;

import java.time.LocalDate;

public class Comment  {
    
	   private int commentId;
	   private String content;
	   private LocalDate commentDate;
	   private int postId;
	   private int comOwnerId;// el user que realizo el comentario
	   private String profPic;
	   private String nickName;
	  
	    public int getCommentId() {
	        return commentId;
	    }

	    public void setCommentId(int commentId) {
	        this.commentId = commentId;
	    }

	    public int getComOwnerId() {
	        return comOwnerId;
	    }

	    public void setComOwnerId (int userId) {
	        this.comOwnerId = userId;
	    }

	    public int getPostId() {
	        return postId;
	    }

	    public void setPostId(int postId) {
	        this.postId = postId;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String commentContent) {
	        this.content = commentContent;
	    }

	    public LocalDate getCommentDate() {
	        return commentDate;
	    }

	    public void setCommentDate(LocalDate commentDate) {
	        this.commentDate = commentDate;
	    }

		public String getProfPic() {
			return profPic;
		}

		public void setProfPic(String profPic) {
			this.profPic = profPic;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
	   
	   
	   
	}
