package com.tao.authenticateservice.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.authenticateservice.security.UserDetailsServiceImpl;
import com.tao.authenticateservice.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;  

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, 
      HttpServletResponse resp, 
      FilterChain chain)
      throws ServletException, IOException {

        log.info("jwt filter");

        String token = req.getHeader("Auth");
        if(null != token) {

          String id = jwtUtil.getUserIdFromToken(token);

          if(id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUserId(Long.parseLong(id));

            log.info("token: " + token);
            if(jwtUtil.validateToken(token, userDetails)) {
              UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                  userDetails, null,  userDetails.getAuthorities());
              authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((req)));
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

          }

        }
        
        chain.doFilter(req, resp);


  }
  
}
