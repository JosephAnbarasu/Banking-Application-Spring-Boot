package com.Banking.service.impl;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.Banking.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {


	private final String SECRET_KEY ="782f54c3c52d281b8763b9fa613d274afd2796ad722fe223e0797f2ce7f31cec";
	
	public String generateToken(User user) {
		String token = Jwts
				.builder()
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+ 24*60*60*1000))
				.signWith(getSigninKey())
				.compact();
		return token;
	}

	private SecretKey getSigninKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private Claims extractClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public <T> T extractClaim(String token, Function<Claims,T> resolver ) {
		Claims claims = extractClaims(token);
		return resolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean isValid(String token,UserDetails user) {
		String username=extractUsername(token);
		return(username.equals(user.getUsername())&& !isTokenExpired(token));
	}
}
