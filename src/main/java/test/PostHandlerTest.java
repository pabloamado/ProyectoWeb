package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import dao.Dao;
import entity.Comment;
import entity.Post;
import handler.PostHandler;
import helper.Helper;
import mock.PostMapperMock;
import mock.PostValidatorMock;
import response.CommentResponse;
import response.GenericResponse;
import response.PostResponse;

class PostHandlerTest {

	private PostMapperMock postMapper=new PostMapperMock(new Dao(), new Helper());
	private PostValidatorMock validator=new PostValidatorMock();
	private PostHandler handler=new PostHandler(postMapper,validator);
	
	@Test
	final void testGetSavePostResponsePostInvalid() {
		String msg="La publicación excede los 240 caracteres permitidos.";
		Post post=new Post();
		post.setPostContent("");
		validator.setValid(false);
		PostResponse response=handler.getSavePostResponse(post);
		assertFalse(response.isSuccess());
		assertNull(response.getPost());
		assertEquals(response.getMsg(),msg);
	}
	
	@Test
	final void testGetSavePostResponsePostValidSavedFailed() {
		String msg="Hubo un error al intentar guardar la publicacion.";
		Post post=new Post();
		post.setPostContent("");
		validator.setValid(true);
		postMapper.setIdGenerated(0);
		PostResponse response=handler.getSavePostResponse(post);
		assertFalse(response.isSuccess());
		assertNull(response.getPost());
		assertEquals(response.getMsg(),msg);
	}
	
	@Test
	final void testGetSavePostResponsePostValidSavedSuccess() {
		Post post=new Post();
		post.setPostContent("");
		validator.setValid(true);
		postMapper.setIdGenerated(1);
		PostResponse response=handler.getSavePostResponse(post);
		assertTrue(response.isSuccess());
		assertNotNull(response.getPost());
	
	}

	@Test
	final void testGetSaveCommentResponseCommentInvalid() {
		String msg="El contenido del posteo no es valido o excede los 240 caracteres";
		Comment comment=new Comment();
		comment.setContent("");
		validator.setValid(false);
		CommentResponse response=handler.getSaveCommentResponse(comment);
		assertFalse(response.isSuccess());
		assertNull(response.getComment());
		assertEquals(response.getMsg(),msg);
	}
	
	@Test
	final void testGetSaveCommentResponseCommentValidSavedFailed() {
		String msg="Hubo un error al intentar publicar el comentario";
		Comment comment=new Comment();
		comment.setContent("sadasdasdasdasd");
		validator.setValid(true);
		postMapper.setIdGenerated(0);
		CommentResponse response=handler.getSaveCommentResponse(comment);
		assertFalse(response.isSuccess());
		assertNull(response.getComment());
		assertEquals(response.getMsg(),msg);
	}
	
	@Test
	final void testGetSaveCommentResponseCommentValidSavedSuccess() {
		Comment comment=new Comment();
		comment.setContent("sadasdasdasdasd");
		validator.setValid(true);
		postMapper.setIdGenerated(1);
		CommentResponse response=handler.getSaveCommentResponse(comment);
		assertTrue(response.isSuccess());
		assertNotNull(response.getComment());
	
	}
	
	@Test
	final void testGetPostsNotNull() {
		postMapper.setPosts(new ArrayList<Post>());
		assertNotNull(handler.getPosts(1));
	}

	@Test
	final void testGetPostsNull() {
		postMapper.setPosts(null);
		assertNull(handler.getPosts(1));
	}

	@Test
	final void testDeletePostSuccess() {
		postMapper.setSuccess(true);
		assertTrue(handler.deletePost(1));
	}
	
	@Test
	final void testDeletePostFailed() {
		postMapper.setSuccess(false);
		assertFalse(handler.deletePost(1));
	}

	@Test
	final void testDeleteCommentSuccess() {
		postMapper.setSuccess(true);
		assertTrue(handler.deleteComment(1));
	}
	
	@Test
	final void testDeleteCommentFailed() {
		postMapper.setSuccess(false);
		assertFalse(handler.deleteComment(1));

	}

	@Test
	final void testGetSaveImageResponseSuccess() {
		postMapper.setSuccess(true);
		PostHandler handler=new PostHandler(postMapper,null);
		GenericResponse response=handler.getSaveImageResponse(0, "imagePath");
		assertTrue(response.isSuccess());
		assertEquals(response.getMsg(),"imagePath");
	}
	
	@Test
	final void testGetSaveImageResponseFailed() {
		postMapper.setSuccess(false);
		PostHandler handler=new PostHandler(postMapper,null);
		GenericResponse response=handler.getSaveImageResponse(0, null);
		assertFalse(response.isSuccess());
		assertEquals(response.getMsg(),null);
	}

}
