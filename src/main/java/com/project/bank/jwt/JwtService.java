package com.project.bank.jwt;

import java.security.Key;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	static final long EXPIRATIONTIME = 60 * 60 * 1000;
	static final String PREFIX = "Bearer";
	static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String getToken(String username) {
		return Jwts.builder()
					.setSubject(username)
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
					.signWith(key)
					.compact();
	}
	
	public String getAuthUser(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(token != null) {
			String username = Jwts.parserBuilder()
									.setSigningKey(key)
									.build()
									.parseClaimsJws(token.replace(PREFIX, ""))
									.getBody()
									.getSubject();
			if(username != null)
				return username;
		}
		return null;
	}
}