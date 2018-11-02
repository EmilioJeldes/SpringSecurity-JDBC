package cl.ejeldes.springsecuritymvc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * A few ways to get the authenticated user
 */
@RestController
@RequestMapping("/user")
public class CredentialsController {

    @GetMapping
    public Map<String, Object> getUser(Authentication authentication) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", authentication.getName());
        user.put("roles", authentication.getAuthorities());
        return user;
    }

    @GetMapping("/authentication")
    public Authentication getAuthenticationByInjection(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/principal")
    public Principal getPrincipalByInjection(Principal principal) {
        return principal;
    }

    @GetMapping("/context/authentication")
    public Authentication getAuthenticationByContextHolder() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/context/principal")
    public User getPrincipalByContextHolder() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (User) principal;
    }
}
