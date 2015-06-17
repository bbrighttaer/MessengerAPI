/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mars.bbrighttaer.rest.messenger.resources;

import java.net.URI;
import org.mars.bbrighttaer.rest.messenger.resources.subresources.CommentResource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.mars.bbrighttaer.rest.messenger.service.MessageService;
import org.mars.bbrighttaer.rest.messenger.model.Message;
import java.util.*;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;
import javax.ws.rs.core.UriInfo;
import org.mars.bbrighttaer.rest.messenger.resources.beans.MessageFilterBean;

/**
 *
 * @author MARS_BRIGHTER
 */
@Path("/messages")//resource URI
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
    
    MessageService messageService  = new MessageService();
    
    @GET//HTTP -> Java method mapping
//    @Produces(MediaType.APPLICATION_JSON)
    //public List<Message> getMessages(@QueryParam("year") int year, @QueryParam("start") int start, @QueryParam("size") int size)
    public List<Message> getMessages(@BeanParam MessageFilterBean filterBean)
    {
        if(filterBean.getYear() > 0) 
        {
            return messageService.getAllMessagesForYear(filterBean.getYear());
        }
        if(filterBean.getStart() > 0 && filterBean.getSize() > 0)
        {
            return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
        }
        return messageService.getAllMessages();
    }
    
    @GET
    @Path("/{messageId}")
//    @Produces(MediaType.APPLICATION_JSON)
    public Message getMessage(@PathParam("messageId")long messageId, @Context UriInfo uriInfo)
    {   
        Message message = messageService.getMessage(messageId);
        String self_uri = getUriForSelf(uriInfo, message);
        String profile_uri = getUriForProfile(uriInfo, message);
        String comments_uri = getUriForComments(uriInfo, message);
        message.addLink(self_uri, "self");
        message.addLink(profile_uri, "profile");
        message.addLink(comments_uri, "comments");
        return message;
    }

    private String getUriForSelf(UriInfo uriInfo, Message message) throws UriBuilderException, IllegalArgumentException 
    {
        String uri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(Long.toString(message.getId()))
                .build()
                .toString();
        return uri;
    }
    private String getUriForProfile(UriInfo uriInfo, Message message) throws UriBuilderException, IllegalArgumentException 
    {
        String uri = uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(message.getAuthor())
                .build()
                .toString();
        return uri;
    }
    private String getUriForComments(UriInfo uriInfo, Message message) throws UriBuilderException, IllegalArgumentException 
    {
        String uri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class, "getCommentResource")
                .path(CommentResource.class)
                .resolveTemplate("messageId", message.getId())//gets the value for the @PathParam("messageId") which determines which comments to load
                .build()
                .toString();
        return uri;
    }
    
    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    public Response addMessages(Message message, @Context UriInfo uriInfo)
    {    
        Message newMessage = messageService.addMessage(message);
        String newId = String.valueOf(newMessage.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();//adds the new ID to the absolute path of this web api
        return Response.created(uri)
                .entity(newMessage)
                .build();
                //.cookie(new NewCookie("name", "Brighter's cookie"))
        //return messageService.addMessage(message);
    }
//    public Message addMessages(Message message)
//    {                
//        return messageService.addMessage(message);
//    }
    
    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId")long messageId, Message message)
    {
        message.setId(messageId);
        return messageService.updateMessage(message);
    }
    
    @DELETE
//    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") long id)
    {
        messageService.removeMessage(id);
    }   
    
    /*
      SUB-RESOURCES
    */
    @Path("/{messageId}/comments")
    public CommentResource getCommentResource()
    {
        return new CommentResource();
    }
}
