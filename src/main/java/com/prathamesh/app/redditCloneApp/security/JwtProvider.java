package com.prathamesh.app.redditCloneApp.security;

import static io.jsonwebtoken.Jwts.parserBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.prathamesh.app.redditCloneApp.exceptions.SpringRedditException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

	private KeyStore keyStore;
	
	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "prathamesh".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
			throw new SpringRedditException("Exception Occurred while loading keystore", e);
		}
	}
	
	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.signWith(getPrivateKey())
				.compact();
	}
	
	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey("springblog", "prathamesh".toCharArray());
		}catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new SpringRedditException("Exception occured while retrieving public key from keystore", e);
		}
	}
	
	public boolean validateToken(String jwt) {
        parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
        return true;
    }
	
	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		}catch (KeyStoreException e) {
			throw new SpringRedditException("Exception occured while retrieving public key from keystore",e);
		}
	}
	
	public String getUsernameFromJwt(String token) {
		 Claims claims = parserBuilder()
	                .setSigningKey(getPublicKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();

	        return claims.getSubject();
	}
}
