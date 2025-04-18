
package com.example.main.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtService {

//	Start main GIT HUB
//	private static final Logger LOGGER = Logger.getLogger(JwtService.class.getName());
//
//  @Value("${JWT_SECRET}")
//  private String secretKeyStr;
//
//  private SecretKey secretKey;
//
//  @PostConstruct
//  public void init() {
//      if (secretKeyStr == null || secretKeyStr.isEmpty()) {
//          LOGGER.log(Level.SEVERE, "JWT secret key is missing or empty");
//          throw new RuntimeException("JWT secret key is missing or empty");
//      }
//      try {
//          byte[] keyBytes = Base64.getDecoder().decode(secretKeyStr);
//          secretKey = Keys.hmacShaKeyFor(keyBytes);
//      } catch (Exception e) {
//          LOGGER.log(Level.SEVERE, "Failed to load JWT secret key", e);
//          throw new RuntimeException("Failed to load JWT secret key", e);
//      }
//  }
//
//  public SecretKey getSecretKey() {
//      return secretKey;
//  }

//	End MAIN GITHUB

//	Start LocalHost

	@Value("${jwt.secret}")
	private String secretkey;

	private SecretKey getSecretKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

//   End LocalHost

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();

		return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365)) // 1 year
				.and().signWith(getSecretKey()).compact();

	}

	public String extractUserName(String token) {
		// extract the username from jwt token
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
