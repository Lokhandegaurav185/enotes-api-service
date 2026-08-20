package com.code.services.impl;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.code.entity.User;
import com.code.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImple implements JwtService{
	
//	@Value("${jwt.secret}")	
	private String secretKey;

	
	public JwtServiceImple() {

		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id",user.getId());
		claims.put("role",user.getRoles());
		claims.put("status",user.getStatus().getIsActive());
		
		String token=Jwts.builder()
		.claims().add(claims)
		.subject(user.getEmail())
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis()+60*60*60*10))
		.and()
		.signWith(getKey())
		.compact();
		return token;
	}

	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
//		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	private Claims extractAllClaims(String token) {
		Claims claims = Jwts.parser()
						.verifyWith((javax.crypto.SecretKey) getKey())
						.build().parseSignedClaims(token).getPayload();
		return claims;
	}

	private SecretKey decrytKey(String secretKey2) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey2);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		Boolean isExpired = isTokenExpired(token);
		if(username.equalsIgnoreCase(userDetails.getUsername())&& !isExpired) {
			return true;
		}
		return false;
	}

	private Boolean isTokenExpired(String token) {
		Claims claims = extractAllClaims(token);
		Date expiredDate = claims.getExpiration();
		
		return expiredDate.before(new Date());
	}

}
