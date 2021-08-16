package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import entity.Comment;
import entity.Post;
import mapper.PostMapper;
import mock.DaoMock;

class PostMapperTest {

	private DaoMock dao=new DaoMock();
	private PostMapper postMapper=new PostMapper(dao, null);
	
	@Test
	final void testSavePostSuccess() {
		dao.setIdGenerated(1);
		int result=postMapper.savePost(new Post());
		assertEquals(result, 1);
	}
	
	@Test
	final void testSavePostFailed() {
		dao.setIdGenerated(0);
		int result=postMapper.savePost(new Post());
		assertEquals(result, 0);
	}

	@Test
	final void testSaveCommentSuccess() {
		dao.setIdGenerated(2);
		int result=postMapper.saveComment(new Comment());
		assertEquals(result, 2);
		
	}
	
	@Test
	final void testSaveCommentFailed() {
		dao.setIdGenerated(0);
		int result=postMapper.saveComment(new Comment());
		assertEquals(result, 0);
		
	}

	@Test
	final void testDeletePostSuccess() {
		dao.setDeleted(true);
		boolean result=postMapper.deletePost(4);
		assertTrue(result);
	}

	@Test
	final void testDeletePostFailed() {
		dao.setDeleted(false);
		boolean result =postMapper.deletePost(3);
		assertFalse(result);
	}
	
	@Test
	final void testDeleteCommentSuccess() {
		dao.setSaved(true);
		boolean result=postMapper.deleteComment(3);
		assertTrue(result);
	}
	
	@Test
	final void testDeleteCommentFailed() {
		dao.setSaved(false);
		boolean result =postMapper.deleteComment(3);
		assertFalse(result);
	}

	@Test
	final void testSaveImgSuccess() {
		dao.setSaved(true);
		boolean result=postMapper.saveImg(1, "foto");
		assertTrue(result);
	}
	
	@Test
	final void testSaveImgFailed() {
		dao.setSaved(false);
		boolean result=postMapper.saveImg(2, "foto");
		assertFalse(result);
	}

}
