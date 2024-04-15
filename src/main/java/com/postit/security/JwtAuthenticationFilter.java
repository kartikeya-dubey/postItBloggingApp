package com.postit.security;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException 
	{
		System.out.println(request);
		System.out.println(response);
		System.out.println(filterChain);
		//Get Token
		String requestToken = request.getHeader("Authorization");
		Enumeration<String> headerNames = request.getHeaderNames();

		while(headerNames.hasMoreElements())
		{
			System.out.println(headerNames.nextElement());
		}
		// Bearer 2352523sdgsg

		System.out.println("Request Token: "+requestToken);
		
		//Fetch user details
		String username=null;
		
		String token=null;
		
		if(request!=null &&(requestToken !=null && requestToken.startsWith("Bearer")))
		{
			token=requestToken.substring(7);
			
			//Exceptions will be generated while fetching username from token
			try
			{
				username=this.jwtTokenHelper.getUsernameFromToken(token);
				
			}
			catch (IllegalArgumentException e) 
			{
				System.out.println("ERROR: Unable to get JWT Token!");
			}
			catch (ExpiredJwtException e) 
			{
				System.out.println("ERROR: JWT Token has expired!");
			}
			catch (MalformedJwtException e) 
			{
				System.out.println("ERROR: Invalid JWT token!");
			}
		}
		else
		{
			System.out.println("ERROR: JWT token does not start with Bearer!");
		}
		
		//Validating token after fetching user details
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			System.out.println("username for validating user details: "+userDetails.getUsername());
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				//User exisits and no security breach, authenticate now
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("ERROR: Invalid JWT Token!");
			}
		}
		else 
		{
			System.out.println("ERROR: User fetch returned null OR Security Authentication retured error!");
		}
		
		//On successful authentication, process the request ahead
		filterChain.doFilter(request, response);
	}

}
