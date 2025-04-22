package com.quizApp.quiz.app.config;

import com.quizApp.quiz.app.services.JwtService;
import com.quizApp.quiz.app.services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter
{
    private final MyUserDetailsService myUserDetailsService;
    private final JwtService jwtService;
    private final ApplicationContext applicationContext;

    @Autowired
    public JwtFilter(final MyUserDetailsService myUserDetailsService, final JwtService jwtService, final ApplicationContext applicationContext)
    {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        // extract the JWT from authorization Header
        String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer "))
        {
            jwtToken = authHeader.substring(7);
            username = jwtService.extractUserName(jwtToken);
        }

        // Authenticate the user
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = applicationContext.getBean(myUserDetailsService.getClass()).loadUserByUsername(username) ;

            // Validate the jwtToken and set Authentication
            if(jwtService.validateToken(jwtToken, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with filter chain
        filterChain.doFilter(request, response);
    }
}
