package handler;

import java.util.ArrayList;
import entity.Comment;
import entity.ExceptionWriter;
import entity.Post;
import mapper.PostMapper;
import response.CommentResponse;
import response.GenericResponse;
import response.PostResponse;
import validator.PostValidator;

public class PostHandler {

	private PostMapper postMapper;
	private PostValidator validator;

	public PostHandler(PostMapper postMapper, PostValidator validator) {
		this.postMapper = postMapper;
		this.validator = validator;
	}

//FUNCION QUE OBTIENE UN RESPONSE DE ACUERDO A SI SE GUARDO O NO LA PUBLICACION
	public PostResponse getSavePostResponse(Post publication) {

		PostResponse response = new PostResponse();
		response.setMsg("Hubo un error al intentar guardar la publicacion.");

		if (!validator.contentIsValid(publication.getPostContent())) {

			response.setMsg("La publicación excede los 240 caracteres permitidos.");

		} else {
			int id = savePost(publication);

			if (id != 0) {
				publication.setPostId(id);
				response.setPost(publication);
				response.setSuccess(true);
			}
		}

		return response;
	}

//FUNCION QUE OBTIENE UN RESPONSE DE ACUERDO A SI SE GUARDO O NO EL COMENTARIO
	public CommentResponse getSaveCommentResponse(Comment comment) {

		CommentResponse response = new CommentResponse();
		response.setMsg("Hubo un error al intentar publicar el comentario");

		if (!validator.contentIsValid(comment.getContent())) {

			response.setMsg("El contenido del posteo no es valido o excede los 240 caracteres");

		} else {

			int id = saveComment(comment);

			if (id != 0) {

				comment.setCommentId(id);
				response.setComment(comment);
				response.setSuccess(true);
			}

		}

		return response;
	}

//LLAMA AL MAPPER PARA GUARDAR EL POST
	private int savePost(Post post) {
		int idGenerated = 0;

		try {

			idGenerated = postMapper.savePost(post);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return idGenerated;
	}

//LLAMA AL MAPPER PARA GUARDAR EL COMENTARIO
	private int saveComment(Comment comment) {
		int idGenerated = 0;

		try {

			idGenerated = postMapper.saveComment(comment);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return idGenerated;
	}

//OBTIENE LA LISTA CON TODAS LAS PUBLICACIONES
	public ArrayList<Post> getPosts(int accountId) {

		ArrayList<Post> posts = new ArrayList<Post>();

		try {
			posts = postMapper.getPosts(accountId);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return posts;
	}

//BORRA UNA PUBLICACION CON TODOS SUS COMENTARIOS
	public boolean deletePost(int postId) {

		boolean deleted = false;

		try {
			deleted = postMapper.deletePost(postId);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return deleted;
	}

	//BORRA UN COMENTARIO
	public boolean deleteComment(int commentId) {

		boolean deleted = false;

		try {
			deleted = postMapper.deleteComment(commentId);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return deleted;
	}

	// OBTIENE UN OBJETO RESPONSE AL GUARDAR LA IMAGEN DE PERFIL
	public GenericResponse getSaveImageResponse(int accountId, String profPic) {

		GenericResponse response = new GenericResponse();

		if (saveProfImg(accountId, profPic)) {

			response.setSuccess(true);
			response.setMsg(profPic);
		}

		return response;
	}

	// LLAMA AL MAPPER PARA GUARDAR LA IMAGEN
	private boolean saveProfImg(int accountId, String profPic) {

		boolean saved = false;

		try {
			saved = postMapper.saveImg(accountId, profPic);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return saved;
	}

}
