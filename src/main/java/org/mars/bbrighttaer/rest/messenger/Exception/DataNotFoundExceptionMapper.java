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
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

    /**
     *
     * @param exception
     * @return
     */
    @Override
    public Response toResponse(DataNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404, "www.google.com");
        return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
    }
    
}
