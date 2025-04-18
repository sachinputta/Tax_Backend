package com.example.main.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String origin = httpServletRequest.getHeader("Origin");

        // Allow specific origin from request
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);

        // Allow credentials (for cookies, authorization headers, etc.)
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // Allow methods
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");

        // Allow headers
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Authorization, Accept, Origin, X-Requested-With");

        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Location");

        // Max age
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");

        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            // Handle preflight requests
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
