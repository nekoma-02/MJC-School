package com.epam.esm.security.util;

import com.epam.esm.entity.Error;
import com.epam.esm.util.ExceptionHandlerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.web.servlet.support.RequestContextUtils.getLocale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityEntryPoint implements AuthenticationEntryPoint {
    private static final long ERROR_CODE = 40100;
    private static final String EXCEPTION_KEY = "exception.bad_credentials";
    @Autowired
    private ExceptionHandlerUtils exceptionHandlerUtils;
    @Autowired
    private MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        String errorMessage;

   //     if(e instanceof BadCredentialsException){
            errorMessage = messageSource.getMessage(EXCEPTION_KEY, null, getLocale(request));
//        }else {
//            errorMessage = exceptionHandlerUtils.extractLocalizedMessage(e, getLocale(request));
//        }

        Error error = new Error(ERROR_CODE, errorMessage);
        exceptionHandlerUtils.sendJsonResponse(error, response, SC_UNAUTHORIZED);
    }
}
