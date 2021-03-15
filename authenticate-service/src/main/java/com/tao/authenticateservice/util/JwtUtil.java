package com.tao.authenticateservice.util;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.tao.authenticateservice.model.User;
import com.tao.authenticateservice.repository.UserRepository;
import com.tao.authenticateservice.security.Token;
import com.tao.authenticateservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

  public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 30;

	@Value("${system.secret}")
	private String secret;

	@Autowired
	private UserRepository userRepository;

	public String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		
		return doGenerateToken(claims, Long.valueOf(user.getId()).toString());
	}


	private String doGenerateToken(Map<String, Object> claims, String subject) {

    String s = Jwts.builder().setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
    return s;
	}

	/*
	public Boolean validateToken(String token, User user) {
		final String userId = getUserIdFromToken(token);
		return (userId.equals(user.getId()) && !isTokenExpired(token));
	}
	*/

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String id = getUserIdFromToken(token);
		User user = userRepository.findUserById(Long.parseLong(id));

		return (user != null && user.getPhonenumber().equals(userDetails.getUsername()) && !isTokenExpired(token));
	}



  public Token parseToken(String token) {
    try {

      String encodedString = Base64.getEncoder().encodeToString(secret.getBytes());
      Claims body = Jwts.parser()
                      .setSigningKey(encodedString)
                      .parseClaimsJws(token)
                      .getBody();


      Token tokenObject = new Token();
      tokenObject.setUsername(body.getSubject());
      tokenObject.setExpiration(new Timestamp((long)body.get("expiration")));

      return tokenObject;

    } catch (JwtException | ClassCastException e) {
      return null;
    }

  }
 
}
