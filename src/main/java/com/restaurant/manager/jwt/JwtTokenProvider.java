package com.restaurant.manager.jwt;

import java.util.Date;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@SuppressWarnings("deprecation")
@Component
public class JwtTokenProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

	// security key
	@Value(value = "${app.jwtSecret}")
	private String jwtSecret;
	// time ended
	@Value(value = "${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	// create jwt from information user
	public String generateToken(UserDetails userPrincipal) {
		Date now = new Date();

		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		// create string json web token from user id
		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	// get user id from jwt
	public String getUserNameFromJWT(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
				.build().parseClaimsJws(token).getBody();
		Function<Claims, String> claimFunction = Claims::getSubject;
		return claimFunction.apply(claims);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			LOGGER.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			LOGGER.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			LOGGER.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			LOGGER.error("JWT claims string is empty");
		}
		return false;
	}
}
