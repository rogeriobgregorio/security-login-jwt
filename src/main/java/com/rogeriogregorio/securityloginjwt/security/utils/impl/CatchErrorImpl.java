package com.rogeriogregorio.securityloginjwt.security.utils.impl;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rogeriogregorio.securityloginjwt.security.exceptions.*;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.MappingException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CatchErrorImpl implements CatchError {

    private static final Logger LOGGER = LogManager.getLogger(CatchErrorImpl.class);
    private static final Map<Class<? extends Exception>, ExceptionCreator> EXCEPTION_MAP = new HashMap<>();

    static {
        EXCEPTION_MAP.put(JWTVerificationException.class, TokenJwtException::new);
        EXCEPTION_MAP.put(JWTCreationException.class, TokenJwtException::new);
        EXCEPTION_MAP.put(TransactionException.class, RepositoryException::new);
        EXCEPTION_MAP.put(DataAccessException.class, RepositoryException::new);
        EXCEPTION_MAP.put(UsernameNotFoundException.class, NotFoundException::new);
        EXCEPTION_MAP.put(ServletException.class, HttpServletException::new);
        EXCEPTION_MAP.put(MappingException.class, DataMapperException::new);
    }

    @Override
    public <T> T run(FunctionWithException<T> method) {

        try {
            return method.run();
        } catch (Exception ex) {
            throwException(ex);
        }
        return null;
    }

    @Override
    public void run(ProcedureWithException method) {

        try {
            method.run();
        } catch (Exception ex) {
            throwException(ex);
        }
    }

    private void throwException(Exception ex) {
        String errorMessage = "Error while executing method " + getCallerMethodName() + ": " + ex.getMessage();
        LOGGER.error(errorMessage, ex);
        throw EXCEPTION_MAP.getOrDefault(ex.getClass(), UnexpectedException::new).create(errorMessage, ex);
    }

    private String getCallerMethodName() {

        Set<String> excludedMethods = Set.of("getStackTrace", "getCallerMethodName", "run");
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .filter(element -> !excludedMethods.contains(element.getMethodName()))
                .skip(1)
                .findFirst()
                .map(StackTraceElement::getMethodName)
                .orElse("unidentified");
    }
}
