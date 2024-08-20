package com.mrymw.handler;

import com.mrymw.security.JWTUtils;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.AbstractHandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class JwtHandshakeHandler extends AbstractHandshakeHandler {

    private final JWTUtils jwtUtils;

    public JwtHandshakeHandler(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
    @Nullable
    protected Principal determineUser(HttpServletRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String token = request.getParameter("token"); // Retrieve token from query parameter or headers
        if (token != null && jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUsernameFromToken(token);
            // Load user details from the database or another source
            UserDetails userDetails = loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        }
        return null;
    }

    private UserDetails loadUserByUsername(String username) {
        return null;
    }
}