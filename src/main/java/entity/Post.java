package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Post {
    
    private int postId;
    private int accountId; 
    private LocalDate postDate;
    private String postContent;
    private String postImg;
    private ArrayList<Comment> comments;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostImg() {
        return postImg;
    }

    public void setPostImg(String postFile) {
        this.postImg = postFile;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    
}
