package com.epiis.apirfds220262.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epiis.apirfds220262.entity.EntityUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {
	private final SecretKey key;
	private final long expirationMilliseconds;

	public JwtTokenService(
		@Value("${app.jwt.secret}") String secret,
		@Value("${app.jwt.expiration-minutes}") long expirationMinutes
	) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.expirationMilliseconds = expirationMinutes * 60L * 1000L;
	}

	public String generate(EntityUser entityUser) {
		Date now = new Date();

		return Jwts.builder()
			.subject(entityUser.getIdUser())
			.claim("role", entityUser.getRole())
			.issuedAt(now)
			.expiration(new Date(now.getTime() + this.expirationMilliseconds))
			.signWith(this.key)
			.compact();
	}

	public String extractIdUser(String token) {
		return parse(token).getSubject();
	}

	public boolean isValid(String token, EntityUser entityUser) {
		Claims claims = parse(token);

		return claims.getSubject().equals(entityUser.getIdUser())
			&& claims.getExpiration().after(new Date());
	}

	private Claims parse(String token) {
		return Jwts.parser()
			.verifyWith(this.key)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
