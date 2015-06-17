/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mars.bbrighttaer.rest.messenger.database;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import org.mars.bbrighttaer.rest.messenger.model.Message;
import org.mars.bbrighttaer.rest.messenger.model.Profile;

/**
 *
 * @author AG BRIGHTER
 */

public class DatabaseClass {
    
    private static Map<Long, Message> messages = new HashMap<>();
    private static Map<String, Profile> profiles = new HashMap<>();
    
    public static Map<Long, Message> getMessages()
    {
        return messages;
    }
    
    public static Map<String, Profile> getProfiles()
    {
        return profiles;
    }
    
}
