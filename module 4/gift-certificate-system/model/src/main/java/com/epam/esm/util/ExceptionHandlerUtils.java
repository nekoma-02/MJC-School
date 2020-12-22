package com.epam.esm.util;

import com.epam.esm.entity.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class ExceptionHandlerUtils {
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ObjectMapper objectMapper;

    public String extractLocalizedMessage(Exception  e, Locale locale){
        String exceptionMessage = e.getMessage();
        return messageSource.getMessage(exceptionMessage, null,  locale);
    }

    public void sendJsonResponse(Error error, HttpServletResponse response, int status) throws IOException {
        response.setStatus(status);
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
