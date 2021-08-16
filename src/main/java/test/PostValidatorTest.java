package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import entity.Comment;
import entity.Post;
import validator.PostValidator;

public class PostValidatorTest {
    
	 private PostValidator validator = new PostValidator();
	 private Post post=new Post();
	 private Comment comment=new Comment();
    @Test
    public void testContentIsValid() {
        comment.setContent("hola es mi primer comentario");
        boolean result = validator.contentIsValid(comment.getContent());
        assertTrue(result);     
    }

    @Test
    public void testContentIsInvalidEmpty() {
          post.setPostContent("");
          boolean result = validator.contentIsValid(post.getPostContent());
          assertFalse(result);  
    }
    
    @Test
    public void testContentIsInvalidLength() {
          post.setPostContent("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
          		+ "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffl"
          		+ "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
          boolean result = validator.contentIsValid(post.getPostContent());
          assertFalse(result);  
    }
}
