/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mars.bbrighttaer.rest.messenger.Exception;

/**
 *
 * @author AG BRIGHTER
 */
public class DataNotFoundException extends RuntimeException{
    
    private static final long serialVersionUID = -6034897190745766939L;

    public DataNotFoundException(String message) {
        super(message);
    }
    
}
