package com.example.taskproject.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class RequestResponseLoggingFilter implements Filter{
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        logger.info("Incoming Request: Method={}, URI={}, Query={}",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                httpRequest.getQueryString());

        chain.doFilter(request, response);

        logger.info("Outgoing Response: Status={}", httpResponse.getStatus());
    }
}
