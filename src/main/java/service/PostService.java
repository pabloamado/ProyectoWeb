package service;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Comment;
import entity.Post;
import factory.HandlerFactory;
import handler.PostHandler;
import response.CommentResponse;
import response.GenericResponse;
import response.PostResponse;

@Path("post")
public class PostService {

	@POST
	@Path("makePost")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response makePost(String jsonPost){

		Gson gson = new Gson();
		PostHandler handler = HandlerFactory.createPostHandler();
		JsonObject jsonObj = gson.fromJson(jsonPost, JsonObject.class);
		int accountId = jsonObj.get("accountId").getAsInt();
		String content = jsonObj.get("postContent").getAsString();
		String time = jsonObj.get("postDate").getAsString();
		LocalDate postDate = LocalDate.parse(time);
		String postImg=jsonObj.get("postImg").getAsString();

		Post post = new Post();
		post.setAccountId(accountId);
		post.setPostContent(content);
		post.setPostDate(postDate);
		post.setPostImg(postImg);
		
		PostResponse response = handler.getSavePostResponse(post);

		return Response.status(Response.Status.OK).entity(gson.toJson(response)).build();
	}
	
	@POST
	@Path("makeComment")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response makeComment(String jsonComment) {
		
		Gson gson = new Gson();
		PostHandler handler = HandlerFactory.createPostHandler();
		JsonObject jsonObj = gson.fromJson(jsonComment, JsonObject.class);
		int accountId = jsonObj.get("accountId").getAsInt();
		String content = jsonObj.get("commentContent").getAsString();
		int postId = jsonObj.get("postId").getAsInt();
		String time = jsonObj.get("commentDate").getAsString();
		LocalDate commentDate = LocalDate.parse(time);
		
		Comment comment=new Comment();
		comment.setContent(content);
		comment.setCommentDate(commentDate);
		comment.setComOwnerId(accountId);
		comment.setPostId(postId);
		CommentResponse response=handler.getSaveCommentResponse(comment);
		
		return  Response.status(Response.Status.OK).entity(gson.toJson(response)).build();
	}
	
	@POST
	@Path("getPosts")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loadPosts(String jsonId) {
		
		Gson gson = new Gson();
		PostHandler handler = HandlerFactory.createPostHandler();
		JsonObject jsonObj = gson.fromJson(jsonId, JsonObject.class);
		int accountId=jsonObj.get("id").getAsInt();
		
		ArrayList<Post> posts = handler.getPosts(accountId);

		return Response.status(Response.Status.OK).entity(gson.toJson(posts)).build();
	}

	@POST
	@Path("saveImg")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveImg(String jsonImg) {

		Gson gson = new Gson();
		PostHandler handler = HandlerFactory.createPostHandler();

		JsonObject jsonObj = gson.fromJson(jsonImg, JsonObject.class);
		int accountId = jsonObj.get("accountId").getAsInt();
		String profPic = jsonObj.get("userImg").getAsString();

		GenericResponse response = handler.getSaveImageResponse(accountId, profPic);

		return Response.status(Response.Status.OK).entity(gson.toJson(response)).build();
	}
	
	@POST
	@Path("deletePost")
	@Consumes(MediaType.APPLICATION_JSON)
 public Response deletePost(String idJson){
		Gson gson = new Gson();
		PostHandler handler = HandlerFactory.createPostHandler();

		JsonObject jsonObj = gson.fromJson(idJson, JsonObject.class);
		int postId=jsonObj.get("postId").getAsInt();
		boolean deleted=handler.deletePost(postId);
		
		return Response.status(Response.Status.OK).entity(gson.toJson(deleted)).build();
	}
	
	@POST
	@Path("deleteComment")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteComment(String idJson){
		Gson gson = new Gson();
		PostHandler handler = HandlerFactory.createPostHandler();
		
		JsonObject jsonObj = gson.fromJson(idJson, JsonObject.class);
		int idComment=jsonObj.get("idCom").getAsInt();
		boolean deleted=handler.deleteComment(idComment);
		
		return Response.status(Response.Status.OK).entity(gson.toJson(deleted)).build();
	}
	
}

