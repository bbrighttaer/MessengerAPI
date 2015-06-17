/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mars.bbrighttaer.rest.messenger.service;

import java.util.*;
import org.mars.bbrighttaer.rest.messenger.Exception.DataNotFoundException;
import org.mars.bbrighttaer.rest.messenger.database.DatabaseClass;
import org.mars.bbrighttaer.rest.messenger.model.Message;
/**
 *
 * @author MARS_BRIGHTER
 */
public class MessageService {
    
    private static Map<Long, Message> messages = DatabaseClass.getMessages();

    public MessageService() {
        messages.put(1L, new Message(1, "Hello world!", "bbrighttaer"));
        messages.put(2L, new Message(2, "Hello jersey", "bbrighttaer"));
    }
    
    
    
    public List<Message> getAllMessages()
    {
//        Message m1= new Message(1L, "Hello world!", "bbrighttaer");
//        Message m2= new Message(2L, "Hello jersey", "bbrighttaer");
//        
//        List<Message> list = new ArrayList<>();
//        list.add(m1);
//        list.add(m2);
//        return list;
        return new ArrayList<Message>(messages.values());
    }
    
    public List<Message> getAllMessagesForYear(int year)
    {
        List<Message> messagesForYear = new ArrayList();
        Calendar cal = Calendar.getInstance();
        for(Message message : messages.values())
        {
            cal.setTime(message.getCreated());
            if(cal.get(Calendar.YEAR) == year)
            {
                messagesForYear.add(message);
            }
        }
        return messagesForYear;
    }
    
    public List<Message> getAllMessagesPaginated(int start, int size)
    {
        ArrayList<Message> list = new ArrayList<Message>(messages.values());
        if(start + size > list.size()) return new ArrayList<Message>();
        return list.subList(start, start + size);
    }
    
    public Message getMessage(long id)
    {
        Message message = messages.get(id);
        if(message==null)
        {
            throw new DataNotFoundException("Message with id "+ id +" not found");
        }
        return message;
    }
    
    public Message addMessage(Message message)
    {
        message.setId(messages.size()+1);
        messages.put(message.getId(), message);
        return message;
    }
    
    public Message updateMessage(Message message)
    {
        if(message.getId()<=0)
        {
            return null;
        }
        messages.put(message.getId(), message);
        return message;
    }
    
    public Message removeMessage(long id){
        return messages.remove(id);
    }
}
