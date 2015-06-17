/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mars.bbrighttaer.rest.messenger.service;

/**
 *
 * @author AG BRIGHTER
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.mars.bbrighttaer.rest.messenger.database.DatabaseClass;
import org.mars.bbrighttaer.rest.messenger.model.Comment;
import org.mars.bbrighttaer.rest.messenger.model.ErrorMessage;
import org.mars.bbrighttaer.rest.messenger.model.Message;

public class CommentService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
            
            /*
            another way of handling exceptions with WebApplicationException
            */
            ErrorMessage errorMessage = new ErrorMessage("NOT FOUND", 404, "www.google.com");
            Response response = Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
            Message message = messages.get(messageId);
            if(message == null)
            {
                throw new WebApplicationException(response); //instead of throwing a custom exception that is registered using the ExceptionMapper and @Provider
            }
		Map<Long, Comment> comments = messages.get(messageId).getComments();
                Comment comment = comments.get(commentId);
                if(comment == null)
                {
                    throw new NotFoundException(response);
                }
		return comments.get(commentId);
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if (comment.getId() <= 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
		
}
