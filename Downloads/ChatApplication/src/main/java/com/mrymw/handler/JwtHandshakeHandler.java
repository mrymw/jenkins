package com.mrymw.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class JwtHandshakeHandler extends DefaultHandshakeHandler {
    protected Principal determineUser(HttpServletRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication;
        }
        return null;
    }
}
