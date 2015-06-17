/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mars.bbrighttaer.rest.messenger.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.mars.bbrighttaer.rest.messenger.model.ErrorMessage;

/**
 *
 * @author AG BRIGHTER
 */
//@Provider //registers this custom exception mapper to jax-rs's exception handlers list
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    //A blanket catch exception handler
    @Override
    public Response toResponse(Throwable exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500, "http://errordocumentationlink.com/throwable/505");
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    }
    
}
